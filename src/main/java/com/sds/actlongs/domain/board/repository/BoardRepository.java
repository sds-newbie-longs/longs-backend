package com.sds.actlongs.domain.board.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.sds.actlongs.domain.board.entity.Board;

public interface BoardRepository extends JpaRepository<Board, Long> {

	@Query("SELECT b "
		+ "FROM boards b "
		+ "WHERE b.id = :boardId AND b.member.id = :memberId AND b.status = 'CREATED'")
	Optional<Board> findByIdAndMemberId(Long boardId, Long memberId);
}
