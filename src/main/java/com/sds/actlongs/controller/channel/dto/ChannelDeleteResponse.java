package com.sds.actlongs.controller.channel.dto;

import static com.sds.actlongs.model.ResultCode.*;

import lombok.Getter;

import com.sds.actlongs.model.ResultResponse;

@Getter
public class ChannelDeleteResponse extends ResultResponse {

	public ChannelDeleteResponse() {
		super(DELETE_CHANNEL_SUCCESS);
	}

}
