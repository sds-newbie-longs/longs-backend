package com.sds.actlongs.controller.member;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

import com.sds.actlongs.controller.member.dto.LoginRequest;
import com.sds.actlongs.controller.member.dto.LoginResponse;
import com.sds.actlongs.domain.member.entity.Member;
import com.sds.actlongs.service.member.MemberService;
import com.sds.actlongs.util.SessionConstants;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

	private final MemberService memberService;

	@PostMapping("/login")
	public ResponseEntity<LoginResponse> login(@RequestBody final LoginRequest dto, HttpServletRequest request) {

		Member member = memberService.login(dto.getUsername());
		if (member == null) {
			return ResponseEntity.ok().body(LoginResponse.fail());
		}

		HttpSession session = request.getSession();
		session.setAttribute(SessionConstants.MEMBER_ID, member.getId());
		return ResponseEntity.ok().body(LoginResponse.succeed());

	}

}
