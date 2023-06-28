package com.sds.actlongs.controller.channelmember.dto;

import static com.sds.actlongs.model.ResultCode.*;

import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

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
	private List<MemberListDto> memberList;

	public MemberListResponse(ResultCode resultCode, List<MemberListDto> memberList) {
		super(resultCode);
		this.memberList = memberList;
	}

	public static MemberListResponse from(List<MemberListDto> channelMemberList) {
		return new MemberListResponse(GET_MEMBERLIST_SUCCESS, channelMemberList);
	}

}
