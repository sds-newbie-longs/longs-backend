package com.sds.actlongs.controller.channel.dto;

import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

import com.sds.actlongs.model.ResultCode;
import com.sds.actlongs.model.ResultResponse;

@Getter
public class ChannelListResponse extends ResultResponse {

	@ApiModelProperty(position = 4, value = "소속그룹 목록", example = ""
		+ "["
		+ "{channelId: 1, ownerId: 1, name: Knox SRE},"
		+ "{channelId: 2, ownerId: 1, name: Knox Common},"
		+ "{channelId: 3, ownerId: 1, name: Knox Portal},"
		+ "]"
	)
	private final List<ChannelDto> channelList;

	public ChannelListResponse(ResultCode resultCode, List<ChannelDto> channelList) {
		super(resultCode);
		this.channelList = channelList;
	}

	public static ChannelListResponse from(List<ChannelDto> channelList) {
		return new ChannelListResponse(ResultCode.CHANNELLIST_SUCCESS, channelList);
	}

}
