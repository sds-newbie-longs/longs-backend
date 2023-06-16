package com.sds.actlongs.controller.channel.dto;

import lombok.Getter;

import com.sds.actlongs.model.ResultCode;
import com.sds.actlongs.model.ResultResponse;

@Getter
public class ChannelCreateResponse extends ResultResponse {

	private ChannelCreateResponse(ResultCode resultCode) {
		super(resultCode);
	}

	public static ChannelCreateResponse of(boolean result) {
		return result ? succeed() : fail();
	}

	private static ChannelCreateResponse succeed() {
		return new ChannelCreateResponse(ResultCode.CHANNELCREATE_SUCCESS);
	}

	private static ChannelCreateResponse fail() {
		return new ChannelCreateResponse(ResultCode.CHANNELCREATE_FAILURE);
	}

}
