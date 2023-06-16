package com.sds.actlongs.controller.channel.dto;

import java.util.Optional;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

import com.sds.actlongs.domain.channel.entity.Channel;
import com.sds.actlongs.model.ResultCode;
import com.sds.actlongs.model.ResultResponse;

@Getter
public class ChannelCreateResponse extends ResultResponse {

	@ApiModelProperty(value = "그룹 PK", example = "")
	private Long channelId;
	@ApiModelProperty(value = "그룹 PK", example = "")
	private Long ownerId;
	@ApiModelProperty(value = "그룹명", example = "")
	private String name;


	private ChannelCreateResponse(ResultCode resultCode, Long channelId, Long ownerId, String name) {
		super(resultCode);
		this.channelId = channelId;
		this.ownerId = ownerId;
		this.name = name;
	}

	public static ChannelCreateResponse of(Optional<Channel> channelOptional) {
		return channelOptional.isPresent() ? succeed(channelOptional.get()) : fail();
	}

	private static ChannelCreateResponse succeed(Channel channel) {
		return new ChannelCreateResponse(ResultCode.CHANNELCREATE_SUCCESS,
			channel.getId(),
			channel.getOwner().getId(),
			channel.getName());
	}

	private static ChannelCreateResponse fail() {
		return new ChannelCreateResponse(ResultCode.CHANNELCREATE_FAILURE, null, null, null);
	}


}
