package com.sds.actlongs.service.board;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import com.sds.actlongs.controller.board.dto.BoardCreateRequest;
import com.sds.actlongs.controller.board.dto.BoardDto;
import com.sds.actlongs.controller.board.dto.MemberBoardsDto;
import com.sds.actlongs.domain.board.entity.Board;
import com.sds.actlongs.domain.board.repository.BoardRepository;
import com.sds.actlongs.domain.channel.repository.ChannelRepository;
import com.sds.actlongs.domain.channelmember.repository.ChannelMemberRepository;
import com.sds.actlongs.domain.member.repository.MemberRepository;
import com.sds.actlongs.domain.video.entity.Video;
import com.sds.actlongs.domain.video.repository.VideoRepository;
import com.sds.actlongs.exception.BoardNotMatchedMemberException;
import com.sds.actlongs.model.ResultCode;
import com.sds.actlongs.util.manage.file.FileManage;
import com.sds.actlongs.util.manage.upload.UploadManage;

@Slf4j
@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

	private final MemberRepository memberRepository;
	private final ChannelRepository channelRepository;
	private final BoardRepository boardRepository;
	private final VideoRepository videoRepository;

	private final FileManage fileManage;
	private final UploadManage uploadManage;

	private final ChannelMemberRepository channelMemberRepository;

	@Override
	public ResultCode createBoard(final BoardCreateRequest request, final Long writerId) {
		memberRepository.findById(writerId)
			.orElseThrow(() -> new ResponseStatusException(HttpStatus.FORBIDDEN, "B011"));
		channelRepository.findById(request.getChannelId())
			.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "B012"));

		//get videoUuid
		Board tempBoard = boardRepository.findById(request.getBoardId())
			.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "B013"));

		Video tempVideo = videoRepository.findByCreatedBoardId(tempBoard.getId())
			.orElseThrow(() -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "B014"));

		if (!fileManage.checkFileExistVideo(tempVideo.getVideoUuid())
			|| !fileManage.checkFileExistImg(tempVideo.getVideoUuid())) {
			return ResultCode.POST_BOARD_FAILURE_BAD_REQUEST_UUID;
		}

		uploadManage.uploadProcess480P(tempVideo.getVideoUuid());

		tempBoard.uploading(request.getTitle(), request.getDescription());
		boardRepository.save(tempBoard);

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
		return channelMemberRepository.findAllFetchMemberUsernameByChannelId(channelId)
			.stream()
			.map(channelMember -> {
				final List<Video> videoList = videoRepository.findByBoardMemberIdOrderByCreatedAtDesc(
					channelMember.getMember().getId(), channelId);
				return new MemberBoardsDto(channelMember.getMember().getUsername(), videoList);
			})
			.collect(Collectors.toList());
	}

	@Override
	public List<Video> searchBoardsIncludeKeywordByChannelId(Long channelId, String keyword) {
		return videoRepository.findAllByChannelIdAndKeywordContainingOrderByCreatedAtDesc(channelId, keyword);
	}

}
