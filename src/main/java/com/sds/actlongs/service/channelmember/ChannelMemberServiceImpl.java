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
		List<Member> membersSearched = memberRepository.findAllByUsernameStartsWith(keyword)
			.stream()
			.filter(m -> m.getId() != memberId)
			.collect(Collectors.toList());

		List<Member> membersBelongsToChannel = channelMemberRepository.findAllChannelByChannelIdAndUsernameStartsWith(
				channelId, keyword)
			.stream()
			.map(ChannelMember::getMember)
			.collect(Collectors.toList());

		membersSearched.removeAll(membersBelongsToChannel);
		return membersSearched;
	}

}
