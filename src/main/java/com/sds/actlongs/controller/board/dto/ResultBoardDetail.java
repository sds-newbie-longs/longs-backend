package com.sds.actlongs.controller.board.dto;

import lombok.Getter;

import com.sds.actlongs.domain.video.entity.Video;

@Getter
public class ResultBoardDetail {

	private final boolean result;
	private final Video video;

	public ResultBoardDetail(boolean result, Video video) {
		this.result = result;
		this.video = video;
	}

}
