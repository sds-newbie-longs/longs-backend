package com.sds.actlongs.service.member;

import com.sds.actlongs.domain.member.entity.Member;

public interface MemberService {
	Member login(final String username);
}
