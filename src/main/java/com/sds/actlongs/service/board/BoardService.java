package com.sds.actlongs.service.board;

import java.util.Optional;

import com.sds.actlongs.domain.board.entity.Board;
import com.sds.actlongs.domain.video.entity.Video;

public interface BoardService {

	Optional<Video> getBoardDetail(final Long boardId);

	Optional<Board> updateBoard(final Board board, final Long memberId);

}
