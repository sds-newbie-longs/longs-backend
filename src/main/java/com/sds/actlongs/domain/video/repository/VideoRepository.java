package com.sds.actlongs.domain.video.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sds.actlongs.domain.video.entity.Video;

public interface VideoRepository extends JpaRepository<Video, Long> {

	Optional<Video> findByBoardId(Long boardId);

	List<Video> findByBoardChannelIdOrderByCreatedAtDesc(Long channelId);

	List<Video> findByBoardMemberIdOrderByCreatedAtDesc(Long memberId);

}
