package com.sds.actlongs.controller.board.dto;

import static com.sds.actlongs.util.Constants.*;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

import com.sds.actlongs.domain.board.entity.Board;
import com.sds.actlongs.domain.video.entity.Video;

@Getter
public class BoardDto {

	@ApiModelProperty(value = "게시글 ID", example = "1")
	private final Long boardId;
	@ApiModelProperty(value = "작성자", example = "harry")
	private final String username;
	@ApiModelProperty(value = "제목", example = "재진스 플레이리스트")
	private final String title;
	@ApiModelProperty(value = "설명", example = "재진스의 뉴진스 플레이리스트 입니다.")
	private final String description;
	@ApiModelProperty(value = "썸네일 url", example = "https://act-longs.s3.ap-northeast-2.amazonaws.com/thumbnails/c0c5afcaaad24d91bfb777440ef3bc12.png")
	private final String thumbnailUrl;
	@ApiModelProperty(value = "상태값", example = "COMPLETED")
	private final Board.Status boardStatus;


	public BoardDto(Video video) {
		this.boardId = video.getBoard().getId();
		this.username = video.getBoard().getMember().getUsername();
		this.title = video.getBoard().getTitle();
		this.description = video.getBoard().getDescription();
		this.thumbnailUrl = CLOUD_FRONT_HOST_THUMBNAIL_URL + CATEGORY_PREFIX + video.getThumbnailImageUuid() + DOT
			+ video.getThumbnailImageType().name().toLowerCase();
		this.boardStatus = video.getBoard().getStatus();
	}

}
