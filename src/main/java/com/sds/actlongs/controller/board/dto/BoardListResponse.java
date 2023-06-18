package com.sds.actlongs.controller.board.dto;

import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

import com.sds.actlongs.model.ResultCode;
import com.sds.actlongs.model.ResultResponse;

@Getter
public class BoardListResponse extends ResultResponse {

	@ApiModelProperty(value = "전체 게시글 검색 목록")
	private final List<BoardDto> allBoardList;
	@ApiModelProperty(value = "멤버별 게시글 검색 목록")
	private final List<MemberBoardsDto> memberBoardList;

	public BoardListResponse(ResultCode resultCode, List<BoardDto> allBoardList,
		List<MemberBoardsDto> memberBoardList) {
		super(resultCode);
		this.allBoardList = allBoardList;
		this.memberBoardList = memberBoardList;
	}

	public static BoardListResponse of(List<BoardDto> allBoardList, List<MemberBoardsDto> memberBoardList) {
		return new BoardListResponse(ResultCode.GET_BOARDLIST_SUCCESS, allBoardList, memberBoardList);
	}

}
