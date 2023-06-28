package com.sds.actlongs.controller.channelmember.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

@Getter
public class MemberListDto {

	@ApiModelProperty(value = "아이디", example = "Harry")
	private String username;
	@ApiModelProperty(value = "PK", example = "1")
	private Long id;

	public MemberListDto(Long id, String username) {
		this.id = id;
		this.username = username;
	}

	public static MemberListDto of(Long id, String username) {
		return new MemberListDto(id, username);
	}

}
