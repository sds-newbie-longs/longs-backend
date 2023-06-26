package com.sds.actlongs.service.channelmember;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import com.sds.actlongs.controller.channelmember.dto.MemberListDto;
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
public class ChannelMemberServiceImpl implements ChannelMemberService {

	private final ChannelMemberRepository channelMemberRepository;
	private final ChannelRepository channelRepository;
	private final MemberRepository memberRepository;

	@Override
	public List<MemberListDto> getMemberList(final Long channelId) {
		final Optional<Channel> optionalChannel = channelRepository.findById(channelId);
		if (optionalChannel.isEmpty() || optionalChannel.get().getStatus().equals(Channel.Status.DELETED)) {
			throw new EntityNotFoundException();
		}
		return channelMemberRepository.findAllFetchMemberUsernameByChannelId(channelId)
			.stream()
			.map(ChannelMember::getMember)
			.map(member -> MemberListDto.of(member.getId(), member.getUsername()))
			.collect(Collectors.toList());
	}

	@Override
	public List<Member> searchMembersNotInChannel(final Long channelId, final Long memberId, final String keyword) {
		List<Member> membersSearched = memberRepository.findAllByUsernameStartsWith(keyword)
			.stream()
			.filter(m -> !m.getId().equals(memberId))
			.collect(Collectors.toList());

		List<Member> membersBelongsToChannel = channelMemberRepository.findAllChannelByChannelIdAndUsernameStartsWith(
				channelId, keyword)
			.stream()
			.map(ChannelMember::getMember)
			.collect(Collectors.toList());

		membersSearched.removeAll(membersBelongsToChannel);
		return membersSearched;
	}

	@Override
	public boolean inviteMember(final Long channelId, final Long memberId) {
		Optional<Member> memberOptional = memberRepository.findById(memberId);
		if (memberOptional.isEmpty()) {
			return false;
		}
		Optional<Channel> channelOptional = channelRepository.findById(channelId);
		if (channelOptional.isEmpty()) {
			return false;
		}
		channelMemberRepository.save(
			ChannelMember.registerMemberToChannel(memberOptional.get(), channelOptional.get()));
		return true;
	}

	@Override
	public boolean leaveChannel(final Long channelId, final Long memberId, final HttpSession session) {
		final Optional<Member> memberOptional = memberRepository.findById(memberId);
		final Optional<ChannelMember> channelMemberOptional = channelMemberRepository.findByChannelIdAndMemberId(
			channelId, memberId);
		if (memberOptional.isEmpty() || channelMemberOptional.isEmpty()
			|| channelMemberOptional.get().getChannel().getStatus().equals(Channel.Status.DELETED)) {
			throw new EntityNotFoundException();
		}

		final ChannelMember channelMember = channelMemberOptional.get();
		channelMemberRepository.deleteById(channelMember.getId());

		Map<Long, Authentication.ChannelRoles> filteredMap = ((Authentication)session.getAttribute(
			SessionConstants.AUTHENTICATION))
			.getChannelAuthorityMap()
			.entrySet()
			.stream()
			.filter(e -> !e.getKey().equals(channelId))
			.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
		session.setAttribute(SessionConstants.AUTHENTICATION, new Authentication(memberId, filteredMap));
		return true;
	}

}
