package com.sds.actlongs.domain.channel.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sds.actlongs.domain.channel.entity.Channel;

public interface ChannelRepository extends JpaRepository<Channel, Long> {

}
