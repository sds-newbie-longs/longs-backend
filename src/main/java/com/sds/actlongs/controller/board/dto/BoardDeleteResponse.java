package com.sds.actlongs.controller.board.dto;

import lombok.Getter;

import com.sds.actlongs.model.ResultCode;
import com.sds.actlongs.model.ResultResponse;

@Getter
public class BoardDeleteResponse extends ResultResponse {

	private BoardDeleteResponse(ResultCode resultCode) {
		super(resultCode);
	}

	public static BoardDeleteResponse of() {
		return new BoardDeleteResponse(ResultCode.DELETE_BOARD_SUCCESS);
	}

}
