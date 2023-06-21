package com.sds.actlongs.domain.member.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sds.actlongs.domain.member.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {

	Optional<Member> findByUsername(String username);

	// @Query("SELECT m FROM members m JOIN FETCH m.id ")
	// List<Member> findAllByMemberChannelId(Long channelId);

	// List<Member> findByUsernameStartsWith(String keyword);

}
