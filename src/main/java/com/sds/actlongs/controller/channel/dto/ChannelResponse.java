package com.sds.actlongs.controller.channel.dto;

import io.swagger.annotations.ApiModelProperty;

import com.sds.actlongs.model.ResultCode;
import com.sds.actlongs.model.ResultResponse;

public class ChannelResponse extends ResultResponse {

	@ApiModelProperty(value = "그룹 PK", example = "1")
	private Long channelId;
	@ApiModelProperty(value = "그룹장 회원PK", example = "10")
	private Long ownerId;
	@ApiModelProperty(value = "그룹명", example = "Knox SRE")
	private String name;

	private ChannelResponse(ResultCode resultCode, Long channelId, Long ownerId, String name) {
		super(resultCode);
		this.channelId = channelId;
		this.ownerId = ownerId;
		this.name = name;
	}

	public static ChannelResponse of(Long channelId, Long ownerId, String name) {
		return new ChannelResponse(ResultCode.CHANNELLIST_SUCCESS, channelId, ownerId, name);
	}

}
