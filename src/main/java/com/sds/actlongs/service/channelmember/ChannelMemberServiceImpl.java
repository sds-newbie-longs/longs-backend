package com.sds.actlongs.service.channelmember;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

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
public class ChannelMemberServiceImpl implements ChannelMemberService {

	private final ChannelMemberRepository channelMemberRepository;
	private final ChannelRepository channelRepository;
	private final MemberRepository memberRepository;

	@Override
	public List<ChannelMember> getMemberList(final Long channelId) {
		final Optional<Channel> optionalChannel = channelRepository.findById(channelId);
		if (optionalChannel.isEmpty() || optionalChannel.get().getStatus().equals(Channel.Status.DELETED)) {
			throw new EntityNotFoundException();
		}
		return channelMemberRepository.findAllFetchMemberUsernameByChannelId(channelId);
	}

	@Override
	public List<Member> searchMembersNotInChannel(final Long channelId, final Long memberId, final String keyword) {
		List<Member> members = channelMemberRepository.findAllCreatedChannel()
			.stream()
			.filter(cm -> cm.getChannel().getId() != channelId)
			.map(ChannelMember::getMember)
			.filter(m -> m.getId() != memberId && m.getUsername().startsWith(keyword))
			.collect(Collectors.toList());

		// 아무 그룹에도 속하지 않은 회원
		// List<Member> membersNoneChannel =  memberRepository.findAll()
		// 	.stream()
		// 	.filter(m -> m.getUsername().startsWith(keyword) && m.getId() != memberId)
		// 	.collect(Collectors.toList());

		return members;

	}

}
