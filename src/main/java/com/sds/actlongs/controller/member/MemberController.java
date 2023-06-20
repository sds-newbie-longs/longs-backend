package com.sds.actlongs.controller.member;

import static com.sds.actlongs.util.SessionConstants.*;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

import com.sds.actlongs.controller.member.dto.LoginRequest;
import com.sds.actlongs.controller.member.dto.LoginResponse;
import com.sds.actlongs.controller.member.dto.MemberInfoResponse;
import com.sds.actlongs.domain.member.entity.Member;
import com.sds.actlongs.model.Authentication;
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
	public ResponseEntity<MemberInfoResponse> memberInfo(
		@SessionAttribute(AUTHENTICATION) Authentication authentication) {
		final Optional<Member> memberOptional = memberService.getMember(authentication.getMemberId());
		return ResponseEntity.ok(MemberInfoResponse.of(memberOptional));
	}

}
