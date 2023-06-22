package com.sds.actlongs.service.board;

import java.util.List;
import java.util.Optional;

import com.sds.actlongs.controller.board.dto.BoardCreateRequest;
import com.sds.actlongs.model.ResultCode;
import com.sds.actlongs.controller.board.dto.BoardDto;
import com.sds.actlongs.controller.board.dto.MemberBoardsDto;
import com.sds.actlongs.domain.board.entity.Board;
import com.sds.actlongs.domain.video.entity.Video;

public interface BoardService {
  
  ResultCode createBoard(final BoardCreateRequest request, Long writerId);

	Optional<Video> getBoardDetail(final Long boardId);

	Board updateBoard(final Board board, final Long memberId);

	boolean deleteBoard(final Long boardId, final Long memberId);

	List<BoardDto> getBoardList(final Long channelId);

	List<MemberBoardsDto> getMemberBoardsList(final Long channelId);

	List<Video> searchBoardsIncludeKeywordByChannelId(Long channelId, String keyword);

}
