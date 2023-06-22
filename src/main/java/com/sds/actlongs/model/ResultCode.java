package com.sds.actlongs.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResultCode {

	// Member
	LOGIN_SUCCESS(200, "L001", "로그인에 성공하였습니다."),
	LOGIN_FAILURE(200, "L002", "로그인에 실패하였습니다."),
	MEMBERINFO_SUCCESS(200, "MI001", "회원정보 조회에 성공하였습니다."),
	MEMBERLIST_SUCCESS(200, "ML001", "그룹원 목록 조회에 성공하였습니다."),
	MEMBERSEARCH_SUCCESS(200, "MS001", "회원 검색에 성공하였습니다."),

	// Channel
	CHANNELCREATE_SUCCESS(200, "CC001", "그룹 생성에 성공하였습니다."),
	CHANNELCREATE_FAILURE(200, "CC002", "그룹 생성에 실패하였습니다."),
	CHANNELLIST_SUCCESS(200, "CL001", "그룹목록 조회에 성공하였습니다."),
	CHANNELDELETE_SUCCESS(200, "CD001", "그룹 삭제에 성공하였습니다."),

	// ChannelMember
	MEMBERINVITE_SUCCESS(200, "IV001", "그룹원 초대에 성공하였습니다."),
	CHANNELLEAVE_SUCCESS(200, "LV001", "그룹 탈퇴에 성공하였습니다."),

	// Board
	GET_BOARDDETAIL_SUCCESS(200, "B001", "게시글 상세정보 조회에 성공하였습니다."),
	GET_BOARDDETAIL_FAIL(200, "B006", "게시글 상세정보 조회에 실패하였습니다."),
	UPDATE_BOARD_SUCCESS(200, "B002", "게시글 수정에 성공하였습니다."),
	UPDATE_BOARD_FAIL(200, "B007", "게시글 수정에 실패하였습니다."),
	DELETE_BOARD_SUCCESS(200, "B003", "게시글 삭제에 성공하였습니다."),
	DELETE_BOARD_FAIL(200, "B008", "게시글 삭제에 실패하였습니다."),
	SEARCH_BOARDLIST_SUCCESS(200, "B004", "게시글 검색에 성공하였습니다."),
	GET_BOARDLIST_SUCCESS(200, "B005", "게시글 리스트 조회에 성공하였습니다."),
	POST_BOARD_SUCCESS(200,"B009","게시글 생성을 완료했습니다."),
	POST_BOARD_FAILURE_BAD_REQUEST_UUID(400,"B010","동영상 Uuid가 존재하지 않습니다.");

	private final int status;
	private final String code;
	private final String message;

}
