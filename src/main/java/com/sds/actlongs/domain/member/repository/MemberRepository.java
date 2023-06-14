package com.sds.actlongs.domain.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sds.actlongs.domain.member.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
	Member findByUsername(String username);
}
