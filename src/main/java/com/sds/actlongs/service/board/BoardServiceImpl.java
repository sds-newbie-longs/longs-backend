package com.sds.actlongs.service.board;

import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import com.sds.actlongs.controller.board.dto.ResultBoardDetail;
import com.sds.actlongs.domain.video.entity.Video;
import com.sds.actlongs.domain.video.repository.VideoRepository;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

	private final VideoRepository videoRepository;

	@Override
	public ResultBoardDetail getBoardDetail(final Long boardId) {
		Optional<Video> videoOptional = videoRepository.findByBoardId(boardId);
		if (videoOptional.isPresent()) {
			return new ResultBoardDetail(true, videoOptional.get());
		} else {
			return new ResultBoardDetail(false, null);
		}
	}

}
