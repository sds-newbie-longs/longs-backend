package com.sds.actlongs.controller.member.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

import com.sds.actlongs.model.ResultCode;
import com.sds.actlongs.model.ResultResponse;

@Getter
public class MemberInfoResponse extends ResultResponse {

	@ApiModelProperty(value = "회원PK", example = "1")
	private Long id;
	@ApiModelProperty(value = "회원 아이디", example = "Harry")
	private String username;

	private MemberInfoResponse(ResultCode resultCode, Long id, String username) {
		super(resultCode);
		this.id = id;
		this.username = username;
	}

	public static MemberInfoResponse of(Long id, String username) {
		return new MemberInfoResponse(ResultCode.MEMBERINFO_SUCCESS, id, username);
	}

}