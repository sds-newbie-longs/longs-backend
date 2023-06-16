package com.sds.actlongs.controller.member;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

import com.sds.actlongs.controller.member.dto.LoginRequest;
import com.sds.actlongs.controller.member.dto.LoginResponse;
import com.sds.actlongs.controller.member.dto.MemberInfoResponse;
import com.sds.actlongs.controller.member.dto.MemberListResponse;
import com.sds.actlongs.controller.member.dto.MemberSearchResponse;
import com.sds.actlongs.service.member.MemberService;

@Api(tags = "회원 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

	private final MemberService memberService;

	@ApiOperation(value = "로그인 API", notes = ""
		+ "L001: 로그인에 성공하였습니다.\n"
		+ "L002: 로그인에 실패하였습니다.")
	@PostMapping("/login")
	public ResponseEntity<LoginResponse> login(@Valid @RequestBody final LoginRequest request,
		final HttpServletRequest servletRequest) {
		final boolean result = memberService.login(request.getUsername(), servletRequest.getSession());
		return ResponseEntity.ok(LoginResponse.from(result));
	}

	@ApiOperation(value = "회원정보 조회 API", notes = ""
		+ "MI001: 회원정보 조회에 성공하였습니다.")
	@GetMapping("/info")
	public ResponseEntity<MemberInfoResponse> memberInfo() {
		return ResponseEntity.ok(MemberInfoResponse.of(1L, "Harry"));
	}

	@ApiOperation(value = "그룹원 목록 조회 API", notes = ""
		+ "ML001: 그룹원 목록 조회에 성공하였습니다.")
	@GetMapping("/in-group/{groupId}")
	public ResponseEntity<MemberListResponse> findMemberList(@PathVariable("groupId") final Long channelId) {

		List<MemberListResponse.MemberResponse> memberList = List.of(
			new MemberListResponse.MemberResponse("Sean"),
			new MemberListResponse.MemberResponse("Ari"),
			new MemberListResponse.MemberResponse("Jin"),
			new MemberListResponse.MemberResponse("Null")
		);
		MemberListResponse listResponse = new MemberListResponse(memberList);
		return ResponseEntity.ok(listResponse);
	}

	@ApiOperation(value = "회원 검색 API", notes = ""
		+ "MS001: 회원 검색에 성공하였습니다.")
	@GetMapping("/{keyword}/not-in-group/{groupId}")
	public ResponseEntity<MemberSearchResponse> searchMember(
		@PathVariable @NotBlank @Size(max = 20) final String keyword,
		@PathVariable("groupId") final Long channelId
	) {

		List<MemberSearchResponse.SearchedMember> searchList = List.of(
			new MemberSearchResponse.SearchedMember(101L, "Din"),
			new MemberSearchResponse.SearchedMember(102L, "diedie"),
			new MemberSearchResponse.SearchedMember(103L, "DIN_DEAN"),
			new MemberSearchResponse.SearchedMember(104L, "dIabcd")
		);
		MemberSearchResponse listResponse = new MemberSearchResponse(searchList);
		return ResponseEntity.ok(listResponse);
	}

}
