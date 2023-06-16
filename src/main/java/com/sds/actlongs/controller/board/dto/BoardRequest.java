package com.sds.actlongs.controller.board.dto;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BoardRequest {

	@NotBlank
	@ApiModelProperty(value = "게시글 pk(공백불가)", example = "1", required = true)
	private Long boardId;

}
