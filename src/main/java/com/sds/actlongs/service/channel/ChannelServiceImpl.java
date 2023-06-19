package com.sds.actlongs.service.channel;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import com.sds.actlongs.domain.channel.entity.Channel;
import com.sds.actlongs.domain.channel.repository.ChannelRepository;
import com.sds.actlongs.domain.channelmember.entity.ChannelMember;
import com.sds.actlongs.domain.channelmember.repository.ChannelMemberRepository;
import com.sds.actlongs.domain.member.entity.Member;
import com.sds.actlongs.domain.member.repository.MemberRepository;

@Service
@RequiredArgsConstructor
public class ChannelServiceImpl implements ChannelService {

	private final MemberRepository memberRepository;
	private final ChannelRepository channelRepository;
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

	@Override
	public boolean createChannel(final String name, final Long ownerId) {
		Optional<Channel> channelOptional = channelRepository.findByName(name);
		if (channelOptional.isPresent()) {
			return false;
		}
		Optional<Member> memberOptional = memberRepository.findById(ownerId);
		if (memberOptional.isEmpty()) {
			return false;
		}

		System.out.println("==================================================");
		Member owner = memberOptional.get();
		System.out.println("==================================================");
		System.out.println(name + " " + owner.getUsername());

		Channel channel = channelRepository.save(Channel.createNewChannel(name, owner));

		System.out.println(channel);
		System.out.println("==================================================");

		channelMemberRepository.save(ChannelMember.registerMemberToChannel(owner, channel));
		return true;
	}

}
