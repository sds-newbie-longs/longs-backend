package com.sds.actlongs.controller.channelmember.dto;

import lombok.Getter;

import com.sds.actlongs.model.ResultCode;
import com.sds.actlongs.model.ResultResponse;

@Getter
public class MemberInviteResponse extends ResultResponse {

	public MemberInviteResponse() {
		super(ResultCode.MEMBERINVITE_SUCCESS);
	}

}
