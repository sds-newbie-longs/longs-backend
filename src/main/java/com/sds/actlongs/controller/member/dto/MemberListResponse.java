package com.sds.actlongs.controller.member.dto;

import java.util.List;

import io.swagger.annotations.ApiModelProperty;

import com.sds.actlongs.model.ResultCode;
import com.sds.actlongs.model.ResultResponse;

public class MemberListResponse extends ResultResponse {

	@ApiModelProperty(value = "그룹원 목록")
	private List<MemberResponse> memberList;

	public MemberListResponse(List<MemberResponse> memberList) {
		super(ResultCode.MEMBERLIST_SUCCESS);
		this.memberList = memberList;
	}

	public static class MemberResponse {

		@ApiModelProperty(value = "아이디", example = "Harry")
		private String username;

		public MemberResponse(String username) {
			this.username = username;
		}

		public static MemberResponse of(String username) {
			return new MemberResponse(username);
		}

	}

}
