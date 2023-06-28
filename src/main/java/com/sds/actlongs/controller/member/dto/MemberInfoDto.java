package com.sds.actlongs.controller.member.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

@Getter
public class MemberInfoDto {

	@ApiModelProperty(value = "아이디", example = "Harry")
	private String username;
	@ApiModelProperty(value = "PK", example = "1")
	private Long id;

	public MemberInfoDto(Long id, String username) {
		this.id = id;
		this.username = username;
	}

	public static MemberInfoDto of(Long id, String username) {
		return new MemberInfoDto(id, username);
	}

}
