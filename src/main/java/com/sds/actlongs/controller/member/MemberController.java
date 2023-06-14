package com.sds.actlongs.controller.member;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

import com.sds.actlongs.controller.member.dto.LoginRequest;
import com.sds.actlongs.controller.member.dto.LoginResponse;
import com.sds.actlongs.service.member.MemberService;

@Api(tags = "회원 API")
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

	private final MemberService memberService;

	@ApiOperation(value = "로그인 API")
	@PostMapping("/login")
	public ResponseEntity<LoginResponse> login(@RequestBody final LoginRequest dto, final HttpServletRequest request) {
		final boolean result = memberService.login(dto.getUsername(), request.getSession());
		return ResponseEntity.ok(LoginResponse.of(result));
	}

}
