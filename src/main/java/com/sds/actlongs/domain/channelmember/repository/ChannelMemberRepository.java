package com.sds.actlongs.domain.channelmember.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sds.actlongs.domain.channelmember.entity.ChannelMember;
import com.sds.actlongs.domain.member.entity.Member;

public interface ChannelMemberRepository extends JpaRepository<ChannelMember, Long> {

	List<ChannelMember> findByMember(Member member);

}
