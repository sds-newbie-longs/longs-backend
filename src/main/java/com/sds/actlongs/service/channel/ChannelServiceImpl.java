package com.sds.actlongs.service.channel;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
		return channelMemberRepository.findAllFetchMemberAndChannelByMemberId(memberId);
	}

	@Override
	@Transactional
	public boolean createChannel(final String channelName, final Long ownerId) {
		Optional<Channel> channelOptional = channelRepository.findByChannelName(channelName);
		if (channelOptional.isPresent()) {
			return false;
		}
		Optional<Member> memberOptional = memberRepository.findById(ownerId);
		if (memberOptional.isEmpty()) {
			return false;
		}

		Member owner = memberOptional.get();
		Channel channel = channelRepository.save(Channel.createNewChannel(channelName, owner));
		channelMemberRepository.save(ChannelMember.registerMemberToChannel(owner, channel));
		return true;
	}

}
