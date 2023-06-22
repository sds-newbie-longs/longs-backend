package com.sds.actlongs.controller.channelmember;

import static com.sds.actlongs.util.SessionConstants.*;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

import com.sds.actlongs.controller.channelmember.dto.ChannelLeaveResponse;
import com.sds.actlongs.controller.channelmember.dto.MemberInviteRequest;
import com.sds.actlongs.controller.channelmember.dto.MemberInviteResponse;
import com.sds.actlongs.controller.channelmember.dto.MemberListResponse;
import com.sds.actlongs.controller.channelmember.dto.MemberSearchResponse;
import com.sds.actlongs.domain.channelmember.entity.ChannelMember;
import com.sds.actlongs.domain.member.entity.Member;
import com.sds.actlongs.model.Authentication;
import com.sds.actlongs.service.channelmember.ChannelMemberService;

@Api(tags = "그룹회원 API")
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/group-members")
public class ChannelMemberController {

	private final ChannelMemberService channelMemberService;

	@ApiOperation(value = "그룹원 목록 조회 API", notes = "ML001: 그룹원 목록 조회에 성공하였습니다.")
	@GetMapping("/{groupId}")
	public ResponseEntity<MemberListResponse> getMemberList(@PathVariable("groupId") final Long channelId) {
		List<ChannelMember> members = channelMemberService.getMemberList(channelId);
		return ResponseEntity.ok(MemberListResponse.from(members));
	}

	@ApiOperation(value = "회원 검색 API", notes = "MS001: 회원 검색에 성공하였습니다.")
	@GetMapping("/not-in/{groupId}/search")
	public ResponseEntity<MemberSearchResponse> searchMember(
		@PathVariable("groupId") final Long channelId,
		@RequestParam @NotBlank @Size(max = 20) final String keyword,
		@SessionAttribute(AUTHENTICATION) Authentication authentication
	) {
		List<Member> externalMembers = channelMemberService.searchMembersNotInChannel(channelId,
			authentication.getMemberId(), keyword);
		return ResponseEntity.ok(MemberSearchResponse.from(externalMembers));
	}

	@ApiOperation(value = "그룹원 초대 API", notes = "IV001: 그룹원 초대에 성공하였습니다.")
	@PostMapping("/{groupId}")
	public ResponseEntity<MemberInviteResponse> inviteMember(
		@PathVariable("groupId") final Long channelId,
		@Valid @RequestBody final MemberInviteRequest request) {
		return ResponseEntity.ok(new MemberInviteResponse());
	}

	@ApiOperation(value = "그룹 탈퇴 API", notes = "LV001: 그룹 탈퇴에 성공하였습니다.")
	@DeleteMapping("/{groupId}")
	public ResponseEntity<ChannelLeaveResponse> leaveChannel(@PathVariable("groupId") final Long channelId) {
		return ResponseEntity.ok(new ChannelLeaveResponse());
	}

}
