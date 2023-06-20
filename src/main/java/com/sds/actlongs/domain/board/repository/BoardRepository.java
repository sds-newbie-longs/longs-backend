package com.sds.actlongs.domain.board.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sds.actlongs.domain.board.entity.Board;

public interface BoardRepository extends JpaRepository<Board, Long> {

	Optional<Board> findByIdAndMemberId(Long boardId, Long memberId);
}
