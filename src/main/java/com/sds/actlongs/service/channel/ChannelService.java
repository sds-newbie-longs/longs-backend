package com.sds.actlongs.service.channel;

import java.util.List;

import com.sds.actlongs.domain.channelmember.entity.ChannelMember;

public interface ChannelService {

	List<ChannelMember> getChannelList(final Long memberId);

	boolean createChannel(final String channelName, final Long ownerId);

	void deleteChannel(final Long channelId, final Long memberId);

}
