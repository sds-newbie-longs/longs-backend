package com.sds.actlongs.service.member;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import com.sds.actlongs.controller.member.dto.MemberInfoDto;
import com.sds.actlongs.domain.channel.entity.Channel;
import com.sds.actlongs.domain.channelmember.entity.ChannelMember;
import com.sds.actlongs.domain.channelmember.repository.ChannelMemberRepository;
import com.sds.actlongs.domain.member.entity.Member;
import com.sds.actlongs.domain.member.repository.MemberRepository;
import com.sds.actlongs.model.Authentication;
import com.sds.actlongs.util.SessionConstants;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

	private final MemberRepository memberRepository;
	private final ChannelMemberRepository channelMemberRepository;

	@Override
	public boolean login(final String username, final HttpSession session) {
		final Optional<Member> memberOptional = memberRepository.findByUsername(username);
		if (memberOptional.isEmpty()) {
			return false;
		}

		final Member member = memberOptional.get();
		final List<Channel> channels = channelMemberRepository.findAllFetchChannelByMemberId(member.getId())
			.stream()
			.map(ChannelMember::getChannel)
			.collect(Collectors.toList());
		session.setAttribute(SessionConstants.AUTHENTICATION, Authentication.of(member, channels));
		return true;
	}

	@Override
	public MemberInfoDto getMember(final Long id) {
		Optional<Member> memberOptional = memberRepository.findById(id);
		return memberOptional.map(member -> new MemberInfoDto(member.getId(), member.getUsername()))
			.orElseThrow(EntityNotFoundException::new);
	}

}
