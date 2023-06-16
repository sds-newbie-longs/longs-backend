package com.sds.actlongs.controller.member.dto;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberListRequest {

	@NotNull
	@ApiModelProperty(value = "그룹 ID", example = "1", required = true)
	private Long channelId;

}
