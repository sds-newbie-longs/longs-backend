package com.sds.actlongs.service.board;

import static com.sds.actlongs.model.ErrorCode.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
	public Optional<Board> updateBoard(final Board board, final Long memberId) {
		Optional<Board> updateBoardOptional = boardRepository.findById(board.getId());
		if (updateBoardOptional.isPresent()) {
			Board updateBoard = updateBoardOptional.get();
			if (updateBoard.getMember().getId() == memberId) {
				updateBoard.updateBoard(board.getTitle(), board.getDescription());
				return Optional.of(updateBoard);
			} else {
				throw new BoardNotMatchedMemberException(BOARD_NOT_MATCHED_MEMBER_FAILURE);
			}
		} else {
			return updateBoardOptional;
		}
	}

	@Override
	public boolean deleteBoard(final Long boardId, final Long memberId) {
		Optional<Board> deleteBoardOptional = boardRepository.findById(boardId);
		if (deleteBoardOptional.isPresent()) {
			Board deleteBoard = deleteBoardOptional.get();
			if (deleteBoard.getMember().getId() == memberId) {
				boardRepository.deleteById(deleteBoard.getId());
				return true;
			} else {
				throw new BoardNotMatchedMemberException(BOARD_NOT_MATCHED_MEMBER_FAILURE);
			}
		} else {
			return false;
		}
	}

	@Override
	public List<BoardDto> getBoardList(final Long channelId) {
		List<Video> videoList = videoRepository.findByBoardChannelIdOrderByCreatedAtDesc(channelId);
		List<BoardDto> boardList = new ArrayList<>();
		videoList.forEach(video -> boardList.add(new BoardDto(video)));
		return boardList;
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
