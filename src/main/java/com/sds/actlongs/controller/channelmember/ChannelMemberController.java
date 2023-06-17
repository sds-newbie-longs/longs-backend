package com.sds.actlongs.controller.channelmember;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

import com.sds.actlongs.controller.channelmember.dto.MemberInviteRequest;
import com.sds.actlongs.controller.channelmember.dto.MemberInviteResponse;

@Api(tags = "그룹회원 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/group-members")
public class ChannelMemberController {

	@ApiOperation(value = "그룹원 초대 API", notes = "IV001: 그룹원 초대에 성공하였습니다.")
	@PostMapping
	public ResponseEntity<MemberInviteResponse> inviteMember(@Valid @RequestBody final MemberInviteRequest request) {
		return ResponseEntity.ok(new MemberInviteResponse());
	}

}
