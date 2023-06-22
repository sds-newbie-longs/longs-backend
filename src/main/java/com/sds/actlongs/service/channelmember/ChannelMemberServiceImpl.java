package com.sds.actlongs.service.channelmember;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import com.sds.actlongs.domain.channel.entity.Channel;
import com.sds.actlongs.domain.channel.repository.ChannelRepository;
import com.sds.actlongs.domain.channelmember.entity.ChannelMember;
import com.sds.actlongs.domain.channelmember.repository.ChannelMemberRepository;

@Service
@RequiredArgsConstructor
public class ChannelMemberServiceImpl implements ChannelMemberService {

	private final ChannelMemberRepository channelMemberRepository;
	private final ChannelRepository channelRepository;

	@Override
	public List<ChannelMember> getMemberList(final Long channelId) {
		final Optional<Channel> optionalChannel = channelRepository.findById(channelId);
		if (optionalChannel.isEmpty() || optionalChannel.get().getStatus().equals(Channel.Status.DELETED)) {
			throw new EntityNotFoundException();
		}
		return channelMemberRepository.findAllFetchMemberUsernameByChannelId(channelId);
	}

}
