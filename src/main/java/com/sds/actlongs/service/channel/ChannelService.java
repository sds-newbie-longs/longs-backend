package com.sds.actlongs.service.channel;

import java.util.List;

import javax.servlet.http.HttpSession;

import com.sds.actlongs.domain.channel.entity.Channel;

public interface ChannelService {

	List<Channel> getChannelList(final Long memberId, final HttpSession session);

	boolean createChannel(final String channelName, final Long ownerId, final HttpSession session);

	void deleteChannel(final Long channelId, final Long memberId, final HttpSession session);

}
