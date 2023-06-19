package com.sds.actlongs.service.board;

import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import com.sds.actlongs.domain.video.entity.Video;
import com.sds.actlongs.domain.video.repository.VideoRepository;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

	private final VideoRepository videoRepository;

	@Override
	public Optional<Video> getBoardDetail(final Long boardId) {
		return videoRepository.findByBoardId(boardId);
	}

}
