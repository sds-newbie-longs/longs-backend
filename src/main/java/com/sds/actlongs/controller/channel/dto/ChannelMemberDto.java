package com.sds.actlongs.controller.channel.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

@Getter
public class ChannelMemberDto {

	@ApiModelProperty(value = "아이디", example = "Harry")
	private String username;
	@ApiModelProperty(value = "PK", example = "1")
	private Long id;

	public ChannelMemberDto(Long id, String username) {
		this.id = id;
		this.username = username;
	}

	public static ChannelMemberDto of(Long id, String username) {
		return new ChannelMemberDto(id, username);
	}

}
