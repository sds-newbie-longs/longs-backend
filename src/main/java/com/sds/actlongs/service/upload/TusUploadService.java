package com.sds.actlongs.service.upload;

import static com.sds.actlongs.vo.ImageExtension.*;
import static com.sds.actlongs.vo.VideoExtension.*;

import java.io.IOException;
import java.sql.Time;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import lombok.RequiredArgsConstructor;
import me.desair.tus.server.TusFileUploadService;
import me.desair.tus.server.exception.TusException;
import me.desair.tus.server.upload.UploadInfo;

import com.sds.actlongs.domain.board.entity.Board;
import com.sds.actlongs.domain.board.repository.BoardRepository;
import com.sds.actlongs.domain.channel.entity.Channel;
import com.sds.actlongs.domain.channel.repository.ChannelRepository;
import com.sds.actlongs.domain.member.entity.Member;
import com.sds.actlongs.domain.member.repository.MemberRepository;
import com.sds.actlongs.domain.video.entity.Video;
import com.sds.actlongs.domain.video.repository.VideoRepository;
import com.sds.actlongs.model.Authentication;
import com.sds.actlongs.model.ResultCode;
import com.sds.actlongs.service.upload.dto.UploadResponseDto;
import com.sds.actlongs.controller.upload.dto.UploadVideoRequestDto;
import com.sds.actlongs.util.duration.DurationExtractor;
import com.sds.actlongs.util.manage.file.FileManage;
import com.sds.actlongs.util.manage.upload.UploadManage;
import com.sds.actlongs.util.thumbnail.ThumbnailExtractor;
import com.sds.actlongs.util.uuid.UuidGenerate;

@Profile({"local", "dev"})
@Service
@RequiredArgsConstructor
public class TusUploadService implements UploadService {

	private final TusFileUploadService tusFileUploadService;
	private final UuidGenerate uuidGenerate;
	private final FileManage fileManage;
	private final UploadManage uploadManage;
	private final ThumbnailExtractor thumbnailExtractor;
	private final DurationExtractor durationExtractor;
	private final BoardRepository boardRepository;
	private final VideoRepository videoRepository;
	private final MemberRepository memberRepository;
	private final ChannelRepository channelRepository;

	@Override
	public UploadResponseDto upload(Authentication authentication, Long groupId, HttpServletRequest request,
		HttpServletResponse response) {
		try {
			tusFileUploadService.process(request, response);
			final UploadInfo uploadInfo = tusFileUploadService.getUploadInfo(request.getRequestURI());
			if (uploadInfo != null && !uploadInfo.isUploadInProgress()) {
				final String uuid = uuidGenerate.getVodUuid();
				fileManage.createTempVideoFileInLocal(tusFileUploadService.getUploadedBytes(request.getRequestURI()),
					uuid);
				thumbnailExtractor.extract(uuid);
				final Time videoDuration = durationExtractor.extractReturnTime(uuid);
				final Member member = memberRepository.findById(authentication.getMemberId())
					.orElseThrow(EntityNotFoundException::new);
				final Channel channel = channelRepository.findById(groupId).orElseThrow(EntityNotFoundException::new);
				final Board board = boardRepository.save(Board.createNewBoard(member, channel));
				videoRepository.save(Video.createNewVideo(board, uuid, PNG, uuid, MP4, videoDuration.toLocalTime()));
				return new UploadResponseDto(board.getId());
			}
		} catch (IOException | TusException e) {
			//TODO
		}
		return new UploadResponseDto();
	}

	@Override
	public ResultCode uploadVideoToS3(Long id,
		UploadVideoRequestDto requestDto) {
		memberRepository.findById(id)
			.orElseThrow(() -> new ResponseStatusException(HttpStatus.FORBIDDEN, "U002"));

		Board tempBoard = boardRepository.findById(requestDto.getBoardId())
			.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "U003"));

		Video tempVideo = videoRepository.findByUploadingBoardId(tempBoard.getId())
			.orElseThrow(() -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "B014"));

		if (!fileManage.checkFileExistVideo(tempVideo.getVideoUuid())) {
			return ResultCode.POST_BOARD_FAILURE_BAD_REQUEST_UUID;
		}

		uploadManage.uploadProcess(tempVideo.getVideoUuid());

		tempBoard.completed();
		boardRepository.save(tempBoard);

		return ResultCode.POST_BOARD_SUCCESS;
	}

}
