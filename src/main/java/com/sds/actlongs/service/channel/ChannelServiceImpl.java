package com.sds.actlongs.service.channel;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import com.sds.actlongs.domain.channelmember.entity.ChannelMember;
import com.sds.actlongs.domain.channelmember.repository.ChannelMemberRepository;

@Service
@RequiredArgsConstructor
public class ChannelServiceImpl implements ChannelService {

	private final ChannelMemberRepository channelMemberRepository;

	@Override
	public List<ChannelMember> getChannelList(final Long memberId) {
		return channelMemberRepository.findAllFetchMemberAndChannelByMemberId(memberId);
	}

}
