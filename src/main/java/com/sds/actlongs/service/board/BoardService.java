package com.sds.actlongs.service.board;

import com.sds.actlongs.controller.board.dto.ResultBoardDetail;

public interface BoardService {

	ResultBoardDetail getBoardDetail(final Long boardId);

}
