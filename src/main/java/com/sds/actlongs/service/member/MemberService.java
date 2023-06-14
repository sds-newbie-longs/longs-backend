package com.sds.actlongs.service.member;

import javax.servlet.http.HttpSession;

public interface MemberService {

	boolean login(final String username, final HttpSession session);

}
