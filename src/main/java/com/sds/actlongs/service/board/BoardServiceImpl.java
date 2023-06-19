package com.sds.actlongs.service.board;

import java.awt.*;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.sds.actlongs.controller.board.dto.BoardCreateRequest;
import com.sds.actlongs.domain.board.entity.Board;
import com.sds.actlongs.domain.board.repository.BoardRepository;
import com.sds.actlongs.domain.channel.entity.Channel;
import com.sds.actlongs.domain.channel.repository.ChannelRepository;
import com.sds.actlongs.domain.member.entity.Member;
import com.sds.actlongs.domain.member.repository.MemberRepository;
import com.sds.actlongs.domain.video.entity.Video;
import com.sds.actlongs.domain.video.repository.VideoRepository;
import com.sds.actlongs.infra.S3Uploader;
import com.sds.actlongs.service.convert.FFmpegConvertService;
import com.sds.actlongs.util.TimeUtils;
import com.sds.actlongs.util.duration.DurationExtractor;
import com.sds.actlongs.util.manage.file.FileManage;
import com.sds.actlongs.util.manage.upload.UploadManage;
import com.sds.actlongs.vo.ImageExtension;
import com.sds.actlongs.vo.VideoExtension;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

	private final FFmpegConvertService convertService;
	private final MemberRepository memberRepository;
	private final ChannelRepository channelRepository;
	private final BoardRepository boardRepository;
	private final VideoRepository videoRepository;

	private final DurationExtractor durationExtractor;
	private final FileManage fileManage;
	private final UploadManage uploadManage;

	@Override
	public void createBoard(BoardCreateRequest request) {
		Member writer =
			memberRepository.findById(
				request.getWriterId()).orElseThrow(()-> new ResponseStatusException(HttpStatus.FORBIDDEN, "B002"));
		Channel channel =
			channelRepository.findById(
				request.getChannelId()).orElseThrow(()-> new ResponseStatusException(HttpStatus.BAD_REQUEST, "B003"));

		if(!fileManage.checkFileExistImg(request.getVideoUuid())
			|| !fileManage.checkFileExistImg(request.getVideoUuid())){
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "B004");
		}

		Time videoDuration = durationExtractor.extractReturnTime(request.getVideoUuid());

		uploadManage.uploadProcess(request.getVideoUuid());

		Board newBoard = (request.getDescription() == null
			? Board.createNewBoard(writer,channel,request.getTitle())
			: Board.createNewBoardWithDescription(writer,channel,request.getTitle(),request.getDescription()));

		Board savedBoard = boardRepository.save(newBoard);

		Video newVideo = Video.createNewVideo(
			savedBoard,
			request.getVideoUuid(),
			ImageExtension.PNG,
			request.getVideoUuid(),
			VideoExtension.MP4,
			videoDuration);

		videoRepository.save(newVideo);
	}

}
