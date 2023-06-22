package com.sds.actlongs.controller.channelmember.dto;

import java.util.List;
import java.util.stream.Collectors;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

import com.sds.actlongs.domain.channelmember.entity.ChannelMember;
import com.sds.actlongs.model.ResultCode;
import com.sds.actlongs.model.ResultResponse;

@Getter
public class MemberListResponse extends ResultResponse {

	@ApiModelProperty(position = 4, value = "그룹원 목록", example = "[\n"
		+ "    {\n"
		+ "      \"id\": 1,\n"
		+ "      \"username\": \"Din\"\n"
		+ "    },\n"
		+ "    {\n"
		+ "      \"id\": 2,\n"
		+ "      \"username\": \"Ari\"\n"
		+ "    },\n"
		+ "    {\n"
		+ "      \"id\": 3,\n"
		+ "      \"username\": \"Kang\"\n"
		+ "    }\n"
		+ "  ]")
	private List<MemberResponse> memberList;

	public MemberListResponse(List<MemberResponse> memberList) {
		super(ResultCode.MEMBERLIST_SUCCESS);
		this.memberList = memberList;
	}

	public static MemberListResponse from(List<ChannelMember> channelMembers) {
		return new MemberListResponse(channelMembers.stream()
			.map(ChannelMember::getMember)
			.map(member -> MemberResponse.from(member.getId(), member.getUsername()))
			.collect(Collectors.toList()));
	}

	@Getter
	public static class MemberResponse {

		@ApiModelProperty(value = "아이디", example = "Harry")
		private String username;
		@ApiModelProperty(value = "PK", example = "1")
		private Long id;

		public MemberResponse(Long id, String username) {
			this.id = id;
			this.username = username;
		}

		public static MemberResponse from(Long id, String username) {
			return new MemberResponse(id, username);
		}

	}

}
