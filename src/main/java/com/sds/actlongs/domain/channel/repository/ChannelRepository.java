package com.sds.actlongs.domain.channel.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sds.actlongs.domain.channel.entity.Channel;

public interface ChannelRepository extends JpaRepository<Channel, Long> {

	Optional<Channel> findByChannelName(final String channelName);

}
