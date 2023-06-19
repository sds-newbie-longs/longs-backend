package com.sds.actlongs.controller.channelmember.dto;

import lombok.Getter;

import com.sds.actlongs.model.ResultCode;
import com.sds.actlongs.model.ResultResponse;

@Getter
public class ChannelLeaveResponse extends ResultResponse {

	public ChannelLeaveResponse() {
		super(ResultCode.CHANNELLEAVE_SUCCESS);
	}

}
