package com.sds.actlongs.controller.board.dto;

import com.sds.actlongs.model.ResultCode;
import com.sds.actlongs.model.ResultResponse;

public class BoardCreateResponse extends ResultResponse {

	private BoardCreateResponse(ResultCode resultCode) {
		super(resultCode);
	}

	public static BoardCreateResponse of(ResultCode resultCode) {
		return new BoardCreateResponse(resultCode);
	}

}
