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
	public Board updateBoard(final Board board, final Long memberId) {
		Optional<Board> updateBoardOptional = boardRepository.findByIdAndMemberId(board.getId(), memberId);
		if (updateBoardOptional.isPresent()) {
			Board updateBoard = updateBoardOptional.get();
			updateBoard.updateBoard(board.getTitle(), board.getDescription());
			return updateBoard;
		} else {
			throw new BoardNotMatchedMemberException();
		}
	}

	@Override
	public boolean deleteBoard(final Long boardId, final Long memberId) {
		Optional<Board> deleteBoardOptional = boardRepository.findByIdAndMemberId(boardId, memberId);
		if (deleteBoardOptional.isPresent()) {
			Board deleteBoard = deleteBoardOptional.get();
			boardRepository.deleteById(deleteBoard.getId());
			return true;
		} else {
			throw new BoardNotMatchedMemberException();
		}
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
		List<ChannelMember> channelMemberList = channelMemberRepository.findByChannelId(channelId);
		List<MemberBoardsDto> memberBoardsList = new ArrayList<>();
		channelMemberList.forEach(channelMember -> {
			List<Video> videoList = videoRepository.findByBoardMemberIdOrderByCreatedAtDesc(
				channelMember.getMember().getId());
			memberBoardsList.add(new MemberBoardsDto(channelMember.getMember().getUsername(), videoList));
		});
		return memberBoardsList;
	}

}
