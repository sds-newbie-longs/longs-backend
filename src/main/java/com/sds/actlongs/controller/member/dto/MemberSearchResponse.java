package com.sds.actlongs.controller.member.dto;

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
	private List<SearchedMember> searchList;

	public MemberSearchResponse(List<SearchedMember> searchList) {
		super(ResultCode.MEMBERSEARCH_SUCCESS);
		this.searchList = searchList;
	}

	public static class SearchedMember {

		@ApiModelProperty(value = "회원PK", example = "1")
		private Long memberId;
		@ApiModelProperty(value = "아이디", example = "Harry")
		private String username;

		public SearchedMember(Long memberId, String username) {
			this.memberId = memberId;
			this.username = username;
		}

		public static SearchedMember of(Long memberId, String username) {
			return new SearchedMember(memberId, username);
		}

	}

}
