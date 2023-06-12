package com.sds.actlongs.domain.group.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sds.actlongs.domain.group.entity.Group;

public interface GroupRepository extends JpaRepository<Group, Long> {

}
