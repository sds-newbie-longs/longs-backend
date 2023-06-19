package com.sds.actlongs.controller.board.dto;

import java.util.ArrayList;
import java.util.List;

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
		List<BoardDto> boardList = new ArrayList<>();
		for (Video video : videoList) {
			boardList.add(new BoardDto(video));
		}
		this.boardList = boardList;
	}

	public static BoardListSearchResponse of(List<Video> videoList) {
		return new BoardListSearchResponse(ResultCode.SEARCH_BOARDLIST_SUCCESS, videoList);
	}

}
