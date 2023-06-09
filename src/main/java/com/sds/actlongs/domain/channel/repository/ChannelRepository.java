package com.sds.actlongs.domain.channel.repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.sds.actlongs.domain.channel.entity.Channel;

@Transactional(readOnly = true)
public interface ChannelRepository extends JpaRepository<Channel, Long> {

	List<Channel> findByIdIn(Collection<Long> ids);

	Optional<Channel> findByChannelName(final String channelName);

	Optional<Channel> findByIdAndOwnerId(Long channelId, Long memberId);

}
