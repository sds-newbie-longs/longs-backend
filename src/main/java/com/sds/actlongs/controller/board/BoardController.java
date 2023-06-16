package com.sds.actlongs.controller.board;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

import com.sds.actlongs.controller.board.dto.BoardDetailResponse;
import com.sds.actlongs.controller.board.dto.BoardRequest;
import com.sds.actlongs.controller.board.dto.BoardUpdateRequest;
import com.sds.actlongs.controller.board.dto.BoardUpdateResponse;

@Api(tags = "게시글 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/boards")
public class BoardController {

	@ApiOperation(value = "상세 조회 API", notes = "B001: 게시글 상세정보 조회에 성공하였습니다.")
	@GetMapping
	public ResponseEntity<BoardDetailResponse> getBoardDetail(@Valid @RequestBody final BoardRequest request) {
		return ResponseEntity.ok(BoardDetailResponse.of(null));
	}

	@ApiOperation(value = "수정 API", notes = "B002: 게시글 수정에 성공하였습니다.")
	@PatchMapping
	public ResponseEntity<BoardUpdateResponse> updateBoard(@Valid @RequestBody final BoardUpdateRequest request) {
		return ResponseEntity.ok(BoardUpdateResponse.of(null));
	}

}
