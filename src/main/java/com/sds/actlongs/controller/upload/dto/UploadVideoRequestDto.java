package com.sds.actlongs.controller.upload.dto;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UploadVideoRequestDto {
	@NotNull
	@ApiModelProperty(value = "게시물 id", example = "1", required = true)
	private Long boardId;
}
