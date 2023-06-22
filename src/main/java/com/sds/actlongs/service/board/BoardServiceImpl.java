package com.sds.actlongs.service.board;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

import com.sds.actlongs.controller.board.dto.BoardDto;
import com.sds.actlongs.controller.board.dto.MemberBoardsDto;
import com.sds.actlongs.domain.board.entity.Board;
import com.sds.actlongs.domain.board.repository.BoardRepository;
import com.sds.actlongs.domain.channelmember.entity.ChannelMember;
import com.sds.actlongs.domain.channelmember.repository.ChannelMemberRepository;
import com.sds.actlongs.domain.video.entity.Video;
import com.sds.actlongs.domain.video.repository.VideoRepository;
import com.sds.actlongs.exception.BoardNotMatchedMemberException;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

	private final BoardRepository boardRepository;
	private final VideoRepository videoRepository;
	private final ChannelMemberRepository channelMemberRepository;

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
