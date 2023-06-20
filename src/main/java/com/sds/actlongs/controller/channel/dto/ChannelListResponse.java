package com.sds.actlongs.controller.channel.dto;

import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

import com.sds.actlongs.domain.channel.entity.Channel;
import com.sds.actlongs.domain.channelmember.entity.ChannelMember;
import com.sds.actlongs.model.ResultCode;
import com.sds.actlongs.model.ResultResponse;

@Getter
public class ChannelListResponse extends ResultResponse {

	@ApiModelProperty(position = 4, value = "소속그룹 목록", example = ""
		+ "["
		+ "{channelId: 1, ownerId: 1, name: Knox SRE},"
		+ "{channelId: 2, ownerId: 1, name: Knox Common},"
		+ "{channelId: 3, ownerId: 1, name: Knox Portal},"
		+ "]"
	)
	private List<JoinedChannel> channelList;

	public ChannelListResponse(List<JoinedChannel> channelList) {
		super(ResultCode.CHANNELLIST_SUCCESS);
		this.channelList = channelList;
	}

	public static ChannelListResponse from(List<ChannelMember> channelMembers) {
		List<JoinedChannel> channelList = List.of();
		for (ChannelMember channelMember : channelMembers) {
			Channel channel = channelMember.getChannel();
			channelList.add(JoinedChannel.of(
				channel.getId(),
				channel.getOwner().getId(),
				channel.getChannelName()
			));
		}
		return new ChannelListResponse(channelList);
	}

	@Getter
	public static class JoinedChannel {

		@ApiModelProperty(value = "그룹PK", example = "1")
		private Long channelId;
		@ApiModelProperty(value = "그룹장 회원PK", example = "11")
		private Long ownerId;
		@ApiModelProperty(value = "그룹명", example = "Knox SRE")
		private String channelName;

		public JoinedChannel(Long channelId, Long ownerId, String channelName) {
			this.channelId = channelId;
			this.ownerId = ownerId;
			this.channelName = channelName;
		}

		public static JoinedChannel of(Long channelId, Long ownerId, String channelName) {
			return new JoinedChannel(channelId, ownerId, channelName);
		}

	}

}
