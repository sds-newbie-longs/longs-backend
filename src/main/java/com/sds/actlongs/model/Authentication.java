package com.sds.actlongs.model;

import static com.sds.actlongs.model.Authentication.ChannelRoles.*;
import static java.util.stream.Collectors.*;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import com.sds.actlongs.domain.channel.entity.Channel;
import com.sds.actlongs.domain.member.entity.Member;

@Getter
@Builder
@AllArgsConstructor
public class Authentication {

	private Long memberId;
	private Map<Long, ChannelRoles> channelAuthorityMap;

	public static Authentication of(Member member, List<Channel> channels) {
		return Authentication.builder()
			.memberId(member.getId())
			.channelAuthorityMap(channels.stream().collect(toMap(Channel::getId, determineChannelRole(member))))
			.build();
	}

	private static Function<Channel, ChannelRoles> determineChannelRole(Member member) {
		return channel -> member.getId().equals(channel.getOwner().getId()) ? OWNER : MEMBER;
	}

	public enum ChannelRoles {
		MEMBER, OWNER
	}

}
