package com.sds.actlongs.domain.video.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.sds.actlongs.domain.video.entity.Video;

public interface VideoRepository extends JpaRepository<Video, Long> {

	@Query("SELECT v "
		+ "FROM videos v "
		+ "INNER JOIN v.board b ON b.status = 'CREATED' "
		+ "WHERE v.board.id = :boardId")
	Optional<Video> findByBoardId(Long boardId);

	@Query("SELECT v "
		+ "FROM videos v "
		+ "INNER JOIN v.board b ON b.channel.id = :channelId AND b.status = 'CREATED' "
		+ "ORDER BY v.createdAt DESC")
	List<Video> findByBoardChannelIdOrderByCreatedAtDesc(Long channelId);

	@Query("SELECT v "
		+ "FROM videos v "
		+ "INNER JOIN v.board b ON b.member.id = :memberId AND b.status = 'CREATED' "
		+ "ORDER BY v.createdAt DESC")
	List<Video> findByBoardMemberIdOrderByCreatedAtDesc(Long memberId);

	@Query("SELECT v "
		+ "FROM videos v "
		+ "INNER JOIN boards b ON v.board.id = b.id AND b.status = 'CREATED' "
		+ "WHERE b.channel.id = :channelId AND b.title LIKE %:keyword% "
		+ "ORDER BY v.createdAt DESC")
	List<Video> findAllByChannelIdAndKeywordContainingOrderByCreatedAtDesc(Long channelId, String keyword);

}
