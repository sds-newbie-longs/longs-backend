package com.sds.actlongs.domain.video.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sds.actlongs.domain.video.entity.Video;

public interface VideoRepository extends JpaRepository<Video, Long> {

	Optional<Video> findByBoard_Id(Long boardId);
}
