package com.sds.actlongs.domain.channelmember.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.sds.actlongs.domain.channelmember.entity.ChannelMember;

public interface ChannelMemberRepository extends JpaRepository<ChannelMember, Long> {

	@Query("SELECT cm "
		+ "FROM channel_members cm "
		+ "JOIN FETCH cm.channel c "
		+ "WHERE cm.member.id = :memberId AND c.status = 'CREATED'")
	List<ChannelMember> findAllFetchChannelByMemberId(Long memberId);

	@Query("SELECT cm "
		+ "FROM channel_members cm "
		+ "INNER JOIN cm.channel c ON c.status = 'CREATED'"
		+ "WHERE cm.channel.id = :channelId")
	List<ChannelMember> findByChannelId(Long channelId);

	@Query("SELECT cm FROM channel_members cm "
		+ "JOIN FETCH cm.channel c JOIN FETCH cm.member m "
		+ "WHERE cm.member.id = :memberId AND c.status = 'CREATED'")
	List<ChannelMember> findAllFetchMemberAndChannelByMemberId(Long memberId);

	@Query("SELECT cm "
		+ "FROM channel_members cm "
		+ "JOIN FETCH cm.member m "
		+ "INNER JOIN cm.channel c ON c.status = 'CREATED'"
		+ "WHERE cm.channel.id = :channelId")
	List<ChannelMember> findAllFetchMemberUsernameByChannelId(Long channelId);

}
