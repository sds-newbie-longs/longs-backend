package com.sds.actlongs.service.channelmember;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import com.sds.actlongs.domain.channelmember.entity.ChannelMember;
import com.sds.actlongs.domain.channelmember.repository.ChannelMemberRepository;

@Service
@RequiredArgsConstructor
public class ChannelMemberServiceImpl implements ChannelMemberService {

	private final ChannelMemberRepository channelMemberRepository;

	@Override
	public List<ChannelMember> getMemberList(final Long channelId) {
		return channelMemberRepository.findAllFetchMemberUsernameByChannelId(channelId);
	}

}
