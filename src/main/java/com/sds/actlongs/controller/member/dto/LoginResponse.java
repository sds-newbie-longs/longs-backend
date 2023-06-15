package com.sds.actlongs.controller.member.dto;

import lombok.Getter;

import com.sds.actlongs.model.ResultCode;
import com.sds.actlongs.model.ResultResponse;

@Getter
public class LoginResponse extends ResultResponse {

	private LoginResponse(ResultCode resultCode) {
		super(resultCode);
	}

	public static LoginResponse of(boolean result) {
		return result ? succeed() : fail();
	}

	private static LoginResponse succeed() {
		return new LoginResponse(ResultCode.LOGIN_SUCCESS);
	}

	private static LoginResponse fail() {
		return new LoginResponse(ResultCode.LOGIN_FAILURE);
	}

}
