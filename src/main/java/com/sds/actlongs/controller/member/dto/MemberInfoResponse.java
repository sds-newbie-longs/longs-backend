package com.sds.actlongs.controller.member.dto;

import static com.sds.actlongs.model.ResultCode.*;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

import com.sds.actlongs.model.ResultCode;
import com.sds.actlongs.model.ResultResponse;

@Getter
public class MemberInfoResponse extends ResultResponse {

	@ApiModelProperty(position = 4, value = "회원 정보", example = "{id: 1, username: Din}")
	private final MemberInfoDto memberInfo;

	public MemberInfoResponse(ResultCode resultCode, MemberInfoDto memberInfo) {
		super(resultCode);
		this.memberInfo = memberInfo;
	}

	public static MemberInfoResponse from(MemberInfoDto memberInfo) {
		return new MemberInfoResponse(GET_MEMBER_INFO_SUCCESS, memberInfo);
	}

}
