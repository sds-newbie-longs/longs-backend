package com.sds.actlongs.service.member;

import java.util.Optional;

import javax.servlet.http.HttpSession;

import com.sds.actlongs.domain.member.entity.Member;

public interface MemberService {

	boolean login(final String username, final HttpSession session);

	Optional<Member> getMember(final Long id);

}
