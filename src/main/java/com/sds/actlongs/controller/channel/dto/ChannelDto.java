package com.sds.actlongs.controller.channel.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

import com.sds.actlongs.domain.channel.entity.Channel;

@Getter
public class ChannelDto {

	@ApiModelProperty(value = "그룹PK", example = "1")
	private final Long channelId;
	@ApiModelProperty(value = "그룹장 회원PK", example = "11")
	private final Long ownerId;
	@ApiModelProperty(value = "그룹명", example = "Knox SRE")
	private final String channelName;

	public ChannelDto(Long channelId, Long ownerId, String channelName) {
		this.channelId = channelId;
		this.ownerId = ownerId;
		this.channelName = channelName;
	}

	public static ChannelDto from(Channel channel) {
		return new ChannelDto(channel.getId(), channel.getOwner().getId(), channel.getChannelName());
	}

}
