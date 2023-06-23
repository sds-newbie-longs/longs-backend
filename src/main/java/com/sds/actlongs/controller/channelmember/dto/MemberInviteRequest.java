package com.sds.actlongs.controller.channelmember.dto;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberInviteRequest {

	@NotNull
	@ApiModelProperty(value = "회원PK", example = "2", required = true)
	private Long memberId;

}
