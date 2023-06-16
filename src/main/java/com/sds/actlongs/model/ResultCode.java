package com.sds.actlongs.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResultCode {

	// Member
	LOGIN_SUCCESS(200, "M001", "로그인에 성공하였습니다."),
	LOGIN_FAILURE(200, "M002", "로그인에 실패하였습니다."),
	// Board
	BOARDDETAIL_SUCCESS(200, "B001", "게시글 상세정보 조회에 성공하였습니다.");

	private final int status;
	private final String code;
	private final String message;
}
