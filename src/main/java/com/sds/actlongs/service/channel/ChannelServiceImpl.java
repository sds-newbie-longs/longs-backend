package com.sds.actlongs.service.channel;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import com.sds.actlongs.domain.channelmember.entity.ChannelMember;
import com.sds.actlongs.domain.channelmember.repository.ChannelMemberRepository;
import com.sds.actlongs.domain.member.entity.Member;
import com.sds.actlongs.domain.member.repository.MemberRepository;

@Service
@RequiredArgsConstructor
public class ChannelServiceImpl implements ChannelService {

	private final MemberRepository memberRepository;
	private final ChannelMemberRepository channelMemberRepository;

	@Override
	public List<ChannelMember> getChannelList(final Long memberId) {
		Optional<Member> memberOptional = memberRepository.findById(memberId);
		if (memberOptional.isEmpty()) {
			return List.of();
		}
		Member member = memberOptional.get();
		return channelMemberRepository.findByMember(member);
	}

}
