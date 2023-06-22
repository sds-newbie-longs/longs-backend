package com.sds.actlongs.service.board;

import javax.servlet.http.HttpSession;

import com.sds.actlongs.controller.board.dto.BoardCreateRequest;
import com.sds.actlongs.model.ResultCode;

public interface BoardService {

	ResultCode createBoard(final BoardCreateRequest request, Long writerId);

}
