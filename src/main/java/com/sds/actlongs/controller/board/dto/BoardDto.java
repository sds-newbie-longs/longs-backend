package com.sds.actlongs.controller.board.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

import com.sds.actlongs.domain.video.entity.Video;
import com.sds.actlongs.vo.ImageExtension;

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
	@ApiModelProperty(value = "동영상 썸네일 이미지 파일 ID", example = "harry_thumbnail")
	private final String thumbnailImageUuid;
	@ApiModelProperty(value = "동영상 썸네일 이미지 파일 유형", example = "PNG")
	private final ImageExtension thumbnailImageType;

	public BoardDto(Video video) {
		this.boardId = video.getBoard().getId();
		this.username = video.getBoard().getMember().getUsername();
		this.title = video.getBoard().getTitle();
		this.description = video.getBoard().getDescription();
		this.thumbnailImageUuid = video.getThumbnailImageUuid();
		this.thumbnailImageType = video.getThumbnailImageType();
	}

}
