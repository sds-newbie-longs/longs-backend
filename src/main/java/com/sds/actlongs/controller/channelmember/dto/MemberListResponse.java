package com.sds.actlongs.controller.channelmember.dto;

import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

import com.sds.actlongs.model.ResultCode;
import com.sds.actlongs.model.ResultResponse;

@Getter
public class MemberListResponse extends ResultResponse {

	@ApiModelProperty(position = 4, value = "그룹원 목록", example = ""
		+ "["
		+ "{ username: Din },"
		+ "{ username: Ari },"
		+ "{ username: Kang }"
		+ "]")
	private List<MemberResponse> memberList;

	public MemberListResponse(List<MemberResponse> memberList) {
		super(ResultCode.MEMBERLIST_SUCCESS);
		this.memberList = memberList;
	}

	@Getter
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
