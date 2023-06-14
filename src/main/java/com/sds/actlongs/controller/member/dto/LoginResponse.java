package com.sds.actlongs.controller.member.dto;

import com.sds.actlongs.model.ResultCode;
import com.sds.actlongs.model.ResultResponse;

public class LoginResponse extends ResultResponse {

	private LoginResponse(ResultCode resultCode) {
		super(resultCode);
	}

	public static LoginResponse succeed() {
		return new LoginResponse(ResultCode.LOGIN_SUCCESS);
	}

	public static LoginResponse fail() {
		return new LoginResponse(ResultCode.LOGIN_FAILURE);
	}

}
