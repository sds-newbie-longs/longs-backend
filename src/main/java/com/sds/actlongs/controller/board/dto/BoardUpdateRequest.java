package com.sds.actlongs.controller.board.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BoardUpdateRequest {

	@NotBlank
	@Size(max = 50)
	@ApiModelProperty(value = "제목(최대50자)", example = "재진스(수정)")
	private String title;

	@Size(max = 1000)
	@ApiModelProperty(value = "설명(최대1000자)", example = "설명(수정)")
	private String description;

}
