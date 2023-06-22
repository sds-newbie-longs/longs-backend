package com.sds.actlongs.service.channelmember;

import java.util.List;

import com.sds.actlongs.domain.channelmember.entity.ChannelMember;
import com.sds.actlongs.domain.member.entity.Member;

public interface ChannelMemberService {

	List<ChannelMember> getMemberList(final Long channelId);

	List<Member> searchMembersNotInChannel(final Long channelId, final Long memberId, final String keyword);

}
