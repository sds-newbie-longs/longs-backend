package com.sds.actlongs.service.channelmember;

import java.util.List;

import javax.servlet.http.HttpSession;

import com.sds.actlongs.controller.channel.dto.ChannelMemberDto;
import com.sds.actlongs.domain.member.entity.Member;

public interface ChannelMemberService {

	List<ChannelMemberDto> getMemberList(final Long channelId);

	List<Member> searchMembersNotInChannel(final Long channelId, final Long memberId, final String keyword);

	boolean inviteMember(final Long channelId, final Long memberId);

	boolean leaveChannel(final Long channelId, final Long memberId, final HttpSession session);

}
