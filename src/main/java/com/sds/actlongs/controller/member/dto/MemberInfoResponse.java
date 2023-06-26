package com.sds.actlongs.controller.member.dto;

import static com.sds.actlongs.model.ResultCode.*;

import java.util.Optional;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

import com.sds.actlongs.domain.member.entity.Member;
import com.sds.actlongs.model.ResultCode;
import com.sds.actlongs.model.ResultResponse;

@Getter
public class MemberInfoResponse extends ResultResponse {

	@ApiModelProperty(position = 4, value = "회원PK", example = "1")
	private Long id;
	@ApiModelProperty(position = 5, value = "회원 아이디", example = "Harry")
	private String username;

	private MemberInfoResponse(ResultCode resultCode, Long id, String username) {
		super(resultCode);
		this.id = id;
		this.username = username;
	}

	public static MemberInfoResponse of(Optional<Member> memberOptional) {
		Member member = memberOptional.get();
		return new MemberInfoResponse(MEMBERINFO_SUCCESS, member.getId(), member.getUsername());
	}

}
