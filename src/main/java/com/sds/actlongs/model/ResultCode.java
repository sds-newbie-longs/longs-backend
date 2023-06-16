package com.sds.actlongs.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResultCode {

	// Member
	LOGIN_SUCCESS(200, "L001", "로그인에 성공하였습니다."),
	LOGIN_FAILURE(200, "L002", "로그인에 실패하였습니다."),
	MEMBERINFO_SUCCESS(200, "MI001", "회원정보 조회에 성공하였습니다.");

	private final int status;
	private final String code;
	private final String message;

}
