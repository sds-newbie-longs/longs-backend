package com.sds.actlongs.service.member;

import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import com.sds.actlongs.domain.member.entity.Member;
import com.sds.actlongs.domain.member.repository.MemberRepository;
import com.sds.actlongs.util.SessionConstants;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

	private final MemberRepository memberRepository;

	@Override
	public boolean login(final String username, final HttpSession session) {
		Optional<Member> memberOptional = memberRepository.findByUsername(username);
		if (memberOptional.isEmpty()) {
			return false;
		}
		Member member = memberOptional.get();
		session.setAttribute(SessionConstants.MEMBER_ID, member.getId());
		return true;
	}

	@Override
	public Optional<Member> getMember(final Long id) {
		return memberRepository.findById(id);
	}

}
