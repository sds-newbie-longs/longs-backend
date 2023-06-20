package com.sds.actlongs.domain.channelmember.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.sds.actlongs.domain.channelmember.entity.ChannelMember;

public interface ChannelMemberRepository extends JpaRepository<ChannelMember, Long> {

	List<ChannelMember> findByChannelId(Long channelId);

	@Query("SELECT cm FROM channel_members cm "
		+ "JOIN FETCH cm.channel c JOIN FETCH cm.member m "
		+ "WHERE cm.member.id = :memberId")
	List<ChannelMember> findAllFetchMemberAndChannelByMemberId(Long memberId);

}
