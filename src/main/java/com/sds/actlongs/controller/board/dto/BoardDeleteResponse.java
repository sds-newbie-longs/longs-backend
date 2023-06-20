package com.sds.actlongs.controller.board.dto;

import static com.sds.actlongs.model.ResultCode.*;

import lombok.Getter;

import com.sds.actlongs.model.ResultCode;
import com.sds.actlongs.model.ResultResponse;

@Getter
public class BoardDeleteResponse extends ResultResponse {

	private BoardDeleteResponse(ResultCode resultCode) {
		super(resultCode);
	}

	public static BoardDeleteResponse of(boolean result) {
		return new BoardDeleteResponse(result ? DELETE_BOARD_SUCCESS : DELETE_BOARD_FAIL);
	}

}
