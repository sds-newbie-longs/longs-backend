package com.sds.actlongs.controller.channel.dto;

import java.util.List;

import io.swagger.annotations.ApiModelProperty;

import com.sds.actlongs.model.ResultCode;
import com.sds.actlongs.model.ResultResponse;

public class ChannelListResponse extends ResultResponse {

	@ApiModelProperty(value = "그룹 목록")
	private List<ChannelResponse> channelList;

	public ChannelListResponse(List<ChannelResponse> channelList) {
		super(ResultCode.CHANNELLIST_SUCCESS);
		this.channelList = channelList;
	}

	public static class ChannelResponse {

		@ApiModelProperty(value = "그룹 PK", example = "1")
		private Long channelId;
		@ApiModelProperty(value = "그룹장 회원PK", example = "10")
		private Long ownerId;
		@ApiModelProperty(value = "그룹명", example = "Knox SRE")
		private String name;

		public ChannelResponse(Long channelId, Long ownerId, String name) {
			this.channelId = channelId;
			this.ownerId = ownerId;
			this.name = name;
		}

		public static ChannelResponse of(Long channelId, Long ownerId, String name) {
			return new ChannelResponse(channelId, ownerId, name);
		}

	}

}
