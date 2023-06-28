package com.sds.actlongs.service.upload.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UploadResponseDto {

	@ApiModelProperty(value = "게시글 PK", example = "1", required = true)
	private Long boardId;

	public UploadResponseDto(Long boardId) {
		this.boardId = boardId;
	}

}
