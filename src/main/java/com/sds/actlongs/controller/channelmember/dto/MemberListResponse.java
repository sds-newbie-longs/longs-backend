package com.sds.actlongs.controller.channelmember.dto;

import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

import com.sds.actlongs.controller.channel.dto.ChannelMemberDto;
import com.sds.actlongs.model.ResultCode;
import com.sds.actlongs.model.ResultResponse;

@Getter
public class MemberListResponse extends ResultResponse {

	@ApiModelProperty(position = 4, value = "그룹원 목록", example = ""
		+ "["
		+ "{id: 1, username: Din},"
		+ "{id: 2, username: Ari},"
		+ "{id: 3, username: Kang},"
		+ "]")
	private List<ChannelMemberDto> memberList;

	public MemberListResponse(ResultCode resultCode, List<ChannelMemberDto> memberList) {
		super(resultCode);
		this.memberList = memberList;
	}

	public static MemberListResponse from(List<ChannelMemberDto> channelMemberList) {
		return new MemberListResponse(ResultCode.MEMBERLIST_SUCCESS, channelMemberList);
	}

}
