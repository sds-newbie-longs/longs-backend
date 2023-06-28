package com.sds.actlongs.controller.channelmember.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

@Getter
public class MemberSearchDto {

	@ApiModelProperty(value = "회원PK", example = "1")
	private final Long memberId;
	@ApiModelProperty(value = "아이디", example = "Harry")
	private final String username;

	public MemberSearchDto(Long memberId, String username) {
		this.memberId = memberId;
		this.username = username;
	}

	public static MemberSearchDto of(Long memberId, String username) {
		return new MemberSearchDto(memberId, username);
	}

}
