package com.sds.actlongs.domain.channelmember.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sds.actlongs.domain.channelmember.entity.ChannelMember;

public interface ChannelMemberRepository extends JpaRepository<ChannelMember, Long> {

	List<ChannelMember> findByChannelId(Long channelId);

}
