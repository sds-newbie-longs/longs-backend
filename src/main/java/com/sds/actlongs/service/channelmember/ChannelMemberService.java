package com.sds.actlongs.service.channelmember;

import java.util.List;

import com.sds.actlongs.domain.channelmember.entity.ChannelMember;

public interface ChannelMemberService {

	List<ChannelMember> getMemberList(final Long channelId);

	List<ChannelMember> searchMembersNotInChannel(final Long channelId, final String keyword);

}
