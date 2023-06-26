package com.sds.actlongs.controller.channelmember.dto;

import static com.sds.actlongs.model.ResultCode.*;

import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

import com.sds.actlongs.model.ResultCode;
import com.sds.actlongs.model.ResultResponse;

@Getter
public class MemberSearchResponse extends ResultResponse {

	@ApiModelProperty(position = 4, value = "회원검색 목록", example = ""
		+ "["
		+ "{ memberId: 101, username: Din },"
		+ "{ memberId: 102, username: diedie },"
		+ "{ memberId: 103, username: DIN_DEAN },"
		+ "{ memberId: 104, username: dIabcd },"
		+ "]")
	private List<MemberSearchDto> memberSearchList;

	public MemberSearchResponse(ResultCode resultCode, List<MemberSearchDto> memberSearchList) {
		super(resultCode);
		this.memberSearchList = memberSearchList;
	}

	public static MemberSearchResponse from(List<MemberSearchDto> memberSearchList) {
		return new MemberSearchResponse(MEMBERSEARCH_SUCCESS, memberSearchList);
	}

}
