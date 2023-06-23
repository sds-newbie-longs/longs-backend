package com.sds.actlongs.controller.channel.dto;

import lombok.Getter;

import com.sds.actlongs.model.ResultCode;
import com.sds.actlongs.model.ResultResponse;

@Getter
public class ChannelDeleteResponse extends ResultResponse {

	public ChannelDeleteResponse() {
		super(ResultCode.CHANNELDELETE_SUCCESS);
	}

}
