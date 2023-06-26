package com.sds.actlongs.service.channel;

import java.util.List;

import javax.servlet.http.HttpSession;

import com.sds.actlongs.controller.channel.dto.ChannelDto;

public interface ChannelService {

	List<ChannelDto> getChannelList(final Long memberId, final HttpSession session);

	boolean createChannel(final String channelName, final Long ownerId, final HttpSession session);

	void deleteChannel(final Long channelId, final Long memberId, final HttpSession session);

}
