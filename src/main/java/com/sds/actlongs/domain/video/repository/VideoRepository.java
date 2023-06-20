package com.sds.actlongs.domain.video.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.sds.actlongs.domain.video.entity.Video;

public interface VideoRepository extends JpaRepository<Video, Long> {

	Optional<Video> findByBoardId(Long boardId);

	List<Video> findByBoardChannelIdOrderByCreatedAtDesc(Long channelId);

	List<Video> findByBoardMemberIdOrderByCreatedAtDesc(Long memberId);

	@Query("SELECT v "
		+ "FROM videos v "
		+ "INNER JOIN boards b ON v.board.id = b.id "
		+ "WHERE b.channel.id = :channelId AND b.title LIKE %:keyword% "
		+ "ORDER BY v.createdAt DESC")
	List<Video> findAllByChannelIdAndKeywordContainingOrderByCreatedAtDesc(Long channelId, String keyword);

}
