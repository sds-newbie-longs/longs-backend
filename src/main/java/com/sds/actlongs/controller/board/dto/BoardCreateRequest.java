package com.sds.actlongs.controller.board.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BoardCreateRequest {

	@NotNull
	@ApiModelProperty(value = "채널 Id", example = "1", required = true)
	private Long channelId;

	@NotNull
	@ApiModelProperty(value = "게시물 id", example = "1", required = true)
	private Long boardId;

	@NotBlank
	@Size(max = 50)
	@ApiModelProperty(value = "제목(최대50자)", example = "재진스", required = true)
	private String title;

	@Size(max = 1000)
	@ApiModelProperty(value = "설명(최대1000자)", example = "후원 문의는 jin1997@example.com")
	private String description;

}
