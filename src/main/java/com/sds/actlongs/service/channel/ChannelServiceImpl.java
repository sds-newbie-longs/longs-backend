package com.sds.actlongs.service.channel;

import java.util.List;
import java.util.Map;
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
import com.sds.actlongs.model.Authentication;
import com.sds.actlongs.util.SessionConstants;

@Service
@RequiredArgsConstructor
public class ChannelServiceImpl implements ChannelService {

	private final MemberRepository memberRepository;
	private final ChannelRepository channelRepository;
	private final ChannelMemberRepository channelMemberRepository;

	@Override
	public List<ChannelDto> getChannelList(final Long memberId, final HttpSession session) {
		Set<Long> channelIds = ((Authentication)session.getAttribute(
			SessionConstants.AUTHENTICATION))
			.getChannelAuthorityMap()
			.keySet();

		return channelRepository.findByIdIn(channelIds)
			.stream()
			.map(channel -> ChannelDto.from(channel))
			.collect(Collectors.toList());
	}

	@Override
	@Transactional
	public boolean createChannel(final String channelName, final Long ownerId, final HttpSession session) {
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

		Map<Long, Authentication.ChannelRoles> map = ((Authentication)session.getAttribute(
			SessionConstants.AUTHENTICATION))
			.getChannelAuthorityMap();
		map.put(channel.getId(), Authentication.ChannelRoles.OWNER);
		session.setAttribute(SessionConstants.AUTHENTICATION, new Authentication(ownerId, map));
		return true;
	}

	@Override
	@Transactional
	public void deleteChannel(final Long channelId, final Long memberId, final HttpSession session) {
		final Channel channel = channelRepository.findByIdAndOwnerId(channelId, memberId)
			.orElseThrow(EntityNotFoundException::new);
		channel.delete();

		Map<Long, Authentication.ChannelRoles> map = ((Authentication)session.getAttribute(
			SessionConstants.AUTHENTICATION))
			.getChannelAuthorityMap()
			.entrySet()
			.stream()
			.filter(e -> !e.getKey().equals(channelId))
			.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
		session.setAttribute(SessionConstants.AUTHENTICATION, new Authentication(memberId, map));
	}

}
