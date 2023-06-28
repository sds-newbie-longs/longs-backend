package com.sds.actlongs.service.member;

import javax.servlet.http.HttpSession;

import com.sds.actlongs.controller.member.dto.MemberInfoDto;

public interface MemberService {

	boolean login(final String username, final HttpSession session);

	MemberInfoDto getMember(final Long id);

}
