package com.sds.actlongs.controller.channelmember.dto;

import static com.sds.actlongs.model.ResultCode.*;

import lombok.Getter;

import com.sds.actlongs.model.ResultCode;
import com.sds.actlongs.model.ResultResponse;

@Getter
public class ChannelLeaveResponse extends ResultResponse {

	public ChannelLeaveResponse(ResultCode resultCode) {
		super(resultCode);
	}

	public static ChannelLeaveResponse from(boolean result) {
		return result ? succeed() : fail();
	}

	private static ChannelLeaveResponse succeed() {
		return new ChannelLeaveResponse(CHANNELLEAVE_SUCCESS);
	}

	private static ChannelLeaveResponse fail() {
		return new ChannelLeaveResponse(CHANNELLEAVE_FAILURE);
	}

}
