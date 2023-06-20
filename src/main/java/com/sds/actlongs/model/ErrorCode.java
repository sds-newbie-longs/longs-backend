package com.sds.actlongs.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

	// Global
	INTERNAL_SERVER_ERROR(500, "E-G001", "내부 서버 오류입니다."),
	INPUT_VALUE_INVALID(400, "E-G002", "입력 값이 유효하지 않습니다."),
	INPUT_TYPE_INVALID(400, "E-G003", "입력 타입이 유효하지 않습니다."),
	METHOD_NOT_ALLOWED(405, "E-G004", "허용되지 않은 HTTP method 입니다."),
	HTTP_MESSAGE_NOT_READABLE(400, "E-G005", "HTTP Request Body 형식이 올바르지 않습니다."),
	REQUEST_PARAMETER_MISSING(400, "E-G006", "요청 파라미터는 필수입니다."),
	REQUEST_HEADER_MISSING(400, "E-G007", "요청 헤더는 필수입니다."),
	ENTITY_NOT_FOUND(404, "E-G008", "존재하지 않는 Entity 입니다."),
	AUTHENTICATION_FAILURE(401, "E-G009", "인증에 실패하였습니다."),

	// Member
	MEMBERINFO_FAILURE(500, "E-M001", "회원정보 조회에 실패하였습니다."),

	// Board
	BOARD_NOT_MATCHED_MEMBER_FAILURE(400, "E-B001", "게시글이 존재하지 않습니다.");

	private final int status;
	private final String code;
	private final String message;
}
