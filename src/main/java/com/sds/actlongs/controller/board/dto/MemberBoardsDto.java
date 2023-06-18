package com.sds.actlongs.controller.board.dto;

import java.util.ArrayList;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

import com.sds.actlongs.domain.video.entity.Video;

@Getter
public class MemberBoardsDto {

	@ApiModelProperty(value = "회원명", example = "harry")
	private final String username;
	@ApiModelProperty(value = "게시글 목록")
	private final List<BoardDto> boardList;

	public MemberBoardsDto(String username, List<Video> videoList) {
		this.username = username;
		List<BoardDto> boardDtoList = new ArrayList<>();
		videoList.forEach(video -> boardDtoList.add(new BoardDto(video)));
		this.boardList = boardDtoList;
	}

}
