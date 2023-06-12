package com.sds.actlongs.domain.video.respository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sds.actlongs.domain.video.entity.Video;

public interface VideoRepository extends JpaRepository<Video, Long> {

}
