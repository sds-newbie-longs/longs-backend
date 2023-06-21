package com.sds.actlongs.controller.board.dto;

import java.util.List;
import java.util.stream.Collectors;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

import com.sds.actlongs.domain.video.entity.Video;
import com.sds.actlongs.model.ResultCode;
import com.sds.actlongs.model.ResultResponse;

@Getter
public class BoardListSearchResponse extends ResultResponse {

	@ApiModelProperty(value = "게시글 목록")
	private final List<BoardDto> boardList;

	public BoardListSearchResponse(ResultCode resultCode, List<Video> videoList) {
		super(resultCode);
		this.boardList = videoList.stream()
			.map(BoardDto::new)
			.collect(Collectors.toList());
	}

	public static BoardListSearchResponse of(List<Video> videoList) {
		return new BoardListSearchResponse(ResultCode.SEARCH_BOARDLIST_SUCCESS, videoList);
	}

}
