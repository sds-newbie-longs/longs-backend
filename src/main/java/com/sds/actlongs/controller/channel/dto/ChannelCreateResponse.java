package com.sds.actlongs.controller.channel.dto;

import static com.sds.actlongs.model.ResultCode.*;

import lombok.Getter;

import com.sds.actlongs.model.ResultCode;
import com.sds.actlongs.model.ResultResponse;

@Getter
public class ChannelCreateResponse extends ResultResponse {

	private ChannelCreateResponse(ResultCode resultCode) {
		super(resultCode);
	}

	public static ChannelCreateResponse from(boolean result) {
		return result ? succeed() : fail();
	}

	private static ChannelCreateResponse succeed() {
		return new ChannelCreateResponse(CHANNELCREATE_SUCCESS);
	}

	private static ChannelCreateResponse fail() {
		return new ChannelCreateResponse(CHANNELCREATE_FAILURE);
	}

}
