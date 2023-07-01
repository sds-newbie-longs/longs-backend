package com.sds.actlongs.util.session;

import java.util.Map;

import javax.servlet.http.HttpSession;

import com.sds.actlongs.model.Authentication;

public interface SessionManage {

	Map<Long, Authentication.ChannelRoles> getAccessibleChannelList(final HttpSession session);

	void addOwnedChannel(final HttpSession session, final Long channelId, final Long ownerId);

	void deleteChannel(final HttpSession session, final Long channelId, final Long memberId);

}
