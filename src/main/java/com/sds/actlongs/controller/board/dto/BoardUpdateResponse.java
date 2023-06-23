package com.sds.actlongs.controller.board.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

import com.sds.actlongs.domain.board.entity.Board;
import com.sds.actlongs.model.ResultCode;
import com.sds.actlongs.model.ResultResponse;

@Getter
public class BoardUpdateResponse extends ResultResponse {

	@ApiModelProperty(value = "제목", example = "재진스(수정)")
	private final String title;
	@ApiModelProperty(value = "설명", example = "설명(수정)")
	private final String description;

	private BoardUpdateResponse(ResultCode resultCode, Board board) {
		super(resultCode);
		this.title = board.getTitle();
		this.description = board.getDescription();
	}

	public static BoardUpdateResponse of(Board board) {
		return new BoardUpdateResponse(ResultCode.UPDATE_BOARD_SUCCESS, board);
	}

}
