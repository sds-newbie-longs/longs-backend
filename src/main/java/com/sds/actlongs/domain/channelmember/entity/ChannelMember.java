package com.sds.actlongs.domain.channelmember.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.NoArgsConstructor;

import com.sds.actlongs.domain.BaseEntity;
import com.sds.actlongs.domain.channel.entity.Channel;
import com.sds.actlongs.domain.member.entity.Member;

@Entity(name = "channel_members")
@Getter
@NoArgsConstructor
public class ChannelMember extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	private Member member;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "channel_id")
	private Channel channel;

	public ChannelMember(Member member, Channel channel) {
		this.member = member;
		this.channel = channel;
	}

	public static ChannelMember registerMemberToChannel(Member member, Channel channel) {
		return new ChannelMember(member, channel);
	}

}
