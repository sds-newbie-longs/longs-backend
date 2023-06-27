package com.sds.actlongs.service.channel;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

import com.sds.actlongs.controller.channel.dto.ChannelDto;
import com.sds.actlongs.domain.channel.entity.Channel;
import com.sds.actlongs.domain.channel.repository.ChannelRepository;
import com.sds.actlongs.domain.channelmember.entity.ChannelMember;
import com.sds.actlongs.domain.channelmember.repository.ChannelMemberRepository;
import com.sds.actlongs.domain.member.entity.Member;
import com.sds.actlongs.domain.member.repository.MemberRepository;
import com.sds.actlongs.util.session.SessionManage;

@Service
@RequiredArgsConstructor
public class ChannelServiceImpl implements ChannelService {

	private final MemberRepository memberRepository;
	private final ChannelRepository channelRepository;
	private final ChannelMemberRepository channelMemberRepository;
	private final SessionManage sessionManage;

	@Override
	public List<ChannelDto> getChannelList(final HttpSession session) {
		final Set<Long> channelIds = sessionManage.getAccessibleChannelList(session).keySet();
		return channelRepository.findByIdIn(channelIds)
			.stream()
			.map(ChannelDto::from)
			.collect(Collectors.toList());
	}

	@Override
	@Transactional
	public boolean createChannel(final String channelName, final Long ownerId, final HttpSession session) {
		final Optional<Channel> channelOptional = channelRepository.findByChannelName(channelName);
		if (channelOptional.isPresent()) {
			return false;
		}
		final Optional<Member> memberOptional = memberRepository.findById(ownerId);
		if (memberOptional.isEmpty()) {
			return false;
		}

		final Member owner = memberOptional.get();
		final Channel channel = channelRepository.save(Channel.createNewChannel(channelName, owner));
		channelMemberRepository.save(ChannelMember.registerMemberToChannel(owner, channel));
		sessionManage.addOwnedChannel(session, channel.getId(), ownerId);
		return true;
	}

	@Override
	@Transactional
	public void deleteChannel(final Long channelId, final Long memberId, final HttpSession session) {
		final Channel channel = channelRepository.findByIdAndOwnerId(channelId, memberId)
			.orElseThrow(EntityNotFoundException::new);
		channel.delete();
		sessionManage.deleteChannel(session, channelId, memberId);
	}

}
