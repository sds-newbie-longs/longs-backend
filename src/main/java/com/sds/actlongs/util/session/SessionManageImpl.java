package com.sds.actlongs.util.session;

import static com.sds.actlongs.model.Authentication.ChannelRoles.*;
import static com.sds.actlongs.util.SessionConstants.*;

import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;

import lombok.Getter;

import com.sds.actlongs.model.Authentication;
import com.sds.actlongs.util.SessionConstants;

@Component
@Getter
public class SessionManageImpl implements SessionManage {

	@Override
	public Map<Long, Authentication.ChannelRoles> getAccessibleChannelList(HttpSession session) {
		return ((Authentication)session.getAttribute(AUTHENTICATION))
			.getChannelAuthorityMap();
	}

	@Override
	public void addOwnedChannel(final HttpSession session, final Long channelId, final Long ownerId) {
		Map<Long, Authentication.ChannelRoles> map = this.getAccessibleChannelList(session);
		map.put(channelId, OWNER);
		session.setAttribute(AUTHENTICATION, new Authentication(ownerId, map));
	}

	@Override
	public void deleteChannel(final HttpSession session, final Long channelId, final Long memberId) {
		final Map<Long, Authentication.ChannelRoles> map = this.getAccessibleChannelList(session)
			.entrySet()
			.stream()
			.filter(e -> !e.getKey().equals(channelId))
			.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
		session.setAttribute(SessionConstants.AUTHENTICATION, new Authentication(memberId, map));
	}

}
