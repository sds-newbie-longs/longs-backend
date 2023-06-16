package com.sds.actlongs.controller.channel.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChannelCreateRequest {

	@NotBlank
	@Size(max = 20)
	@ApiModelProperty(value = "그룹명(최대 20자)", example = "Knox SRE", required = true)
	private String name;

}
