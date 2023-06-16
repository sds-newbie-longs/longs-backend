package com.sds.actlongs.controller.board;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;

import com.sds.actlongs.controller.board.dto.BoardListResponse;

@Api(tags = "게시글 리스트 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/boardList")
public class BoardListController {

	@ApiOperation(value = "게시글 목록 (메인페이지) API", notes = "B001: 게시글 상세정보 조회에 성공하였습니다.")
	@GetMapping
	public ResponseEntity<BoardListResponse> getBoardList(
		@Valid @NotBlank @ApiParam(value = "그룹 id", example = "1", required = true) Long channelId) {
		return ResponseEntity.ok(null);
	}

}
