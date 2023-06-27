package com.sds.actlongs.controller.channelmember.dto;

import static com.sds.actlongs.model.ResultCode.*;

import lombok.Getter;

import com.sds.actlongs.model.ResultCode;
import com.sds.actlongs.model.ResultResponse;

@Getter
public class MemberInviteResponse extends ResultResponse {

	public MemberInviteResponse(ResultCode resultCode) {
		super(resultCode);
	}

	public static MemberInviteResponse from(boolean result) {
		return result ? succeed() : fail();
	}

	private static MemberInviteResponse succeed() {
		return new MemberInviteResponse(INVITE_MEMBER_SUCCESS);
	}

	private static MemberInviteResponse fail() {
		return new MemberInviteResponse(INVITE_MEMBER_FAILURE);
	}

}
