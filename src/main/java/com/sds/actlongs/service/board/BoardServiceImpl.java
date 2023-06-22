package com.sds.actlongs.service.board;

import java.sql.Time;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.sds.actlongs.controller.board.dto.BoardCreateRequest;
import com.sds.actlongs.domain.board.entity.Board;
import com.sds.actlongs.domain.board.repository.BoardRepository;
import com.sds.actlongs.domain.channel.entity.Channel;
import com.sds.actlongs.domain.channel.repository.ChannelRepository;
import com.sds.actlongs.domain.member.entity.Member;
import com.sds.actlongs.domain.member.repository.MemberRepository;
import com.sds.actlongs.domain.video.entity.Video;
import com.sds.actlongs.domain.video.repository.VideoRepository;
import com.sds.actlongs.model.ResultCode;
import com.sds.actlongs.util.duration.DurationExtractor;
import com.sds.actlongs.util.manage.file.FileManage;
import com.sds.actlongs.util.manage.upload.UploadManage;
import com.sds.actlongs.vo.ImageExtension;
import com.sds.actlongs.vo.VideoExtension;
import com.sds.actlongs.controller.board.dto.BoardDto;
import com.sds.actlongs.controller.board.dto.MemberBoardsDto;
import com.sds.actlongs.domain.channelmember.repository.ChannelMemberRepository;
import com.sds.actlongs.exception.BoardNotMatchedMemberException;

@Slf4j
@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

	private final MemberRepository memberRepository;
	private final ChannelRepository channelRepository;
	private final BoardRepository boardRepository;
	private final VideoRepository videoRepository;

	private final DurationExtractor durationExtractor;
	private final FileManage fileManage;
	private final UploadManage uploadManage;

	private final ChannelMemberRepository channelMemberRepository;


	@Override
	public ResultCode createBoard(final BoardCreateRequest request, final Long writerId) {
		Member writer =
			memberRepository.findById(
				writerId).orElseThrow(() -> new ResponseStatusException(HttpStatus.FORBIDDEN, "B007"));
		Channel channel =
			channelRepository.findById(
				request.getChannelId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "B008"));

		if (!fileManage.checkFileExistImg(request.getVideoUuid())
			|| !fileManage.checkFileExistImg(request.getVideoUuid())) {
			return ResultCode.POST_BOARD_FAILURE_BAD_REQUEST_UUID;
		}

		Time videoDuration = durationExtractor.extractReturnTime(request.getVideoUuid());
		System.out.println("videoDuration:" + videoDuration.toString());
		uploadManage.uploadProcess(request.getVideoUuid());

		Board newBoard = (request.getDescription() == null
			? Board.createNewBoard(writer, channel, request.getTitle())
			: Board.createNewBoardWithDescription(writer, channel, request.getTitle(), request.getDescription()));

		Board savedBoard = boardRepository.save(newBoard);

		Video newVideo = Video.createNewVideo(
			savedBoard,
			request.getVideoUuid(),
			ImageExtension.PNG,
			request.getVideoUuid(),
			VideoExtension.MP4,
			videoDuration);

		videoRepository.save(newVideo);

		return ResultCode.POST_BOARD_SUCCESS;
  }

	@Override
	public Optional<Video> getBoardDetail(final Long boardId) {
		return videoRepository.findByBoardId(boardId);
	}

	@Override
	@Transactional
	public Board updateBoard(Board updateBoard, final Long memberId) {
		final Board board = boardRepository.findByIdAndMemberId(updateBoard.getId(), memberId)
			.orElseThrow(BoardNotMatchedMemberException::new);
		return board.updateBoard(updateBoard.getTitle(), updateBoard.getDescription());
	}

	@Override
	@Transactional
	public boolean deleteBoard(final Long boardId, final Long memberId) {
		final Board board = boardRepository.findByIdAndMemberId(boardId, memberId)
			.orElseThrow(BoardNotMatchedMemberException::new);
		board.delete();
		return true;
	}

	@Override
	public List<BoardDto> getBoardList(final Long channelId) {
		return videoRepository.findByBoardChannelIdOrderByCreatedAtDesc(channelId)
			.stream()
			.map(BoardDto::new)
			.collect(Collectors.toList());
	}

	@Override
	public List<MemberBoardsDto> getMemberBoardsList(final Long channelId) {
		return channelMemberRepository.findByChannelId(channelId)
			.stream()
			.map(channelMember -> {
				final List<Video> videoList = videoRepository.findByBoardMemberIdOrderByCreatedAtDesc(
					channelMember.getMember().getId());
				return new MemberBoardsDto(channelMember.getMember().getUsername(), videoList);
			})
			.collect(Collectors.toList());
	}

	@Override
	public List<Video> searchBoardsIncludeKeywordByChannelId(Long channelId, String keyword) {
		return videoRepository.findAllByChannelIdAndKeywordContainingOrderByCreatedAtDesc(channelId, keyword);
	}

}
