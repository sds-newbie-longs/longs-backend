package com.sds.actlongs.controller.board;

import java.sql.Time;
import java.time.LocalTime;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

import com.sds.actlongs.controller.board.dto.BoardDeleteResponse;
import com.sds.actlongs.controller.board.dto.BoardDetailResponse;
import com.sds.actlongs.controller.board.dto.BoardRequest;
import com.sds.actlongs.controller.board.dto.BoardUpdateRequest;
import com.sds.actlongs.controller.board.dto.BoardUpdateResponse;
import com.sds.actlongs.domain.board.entity.Board;
import com.sds.actlongs.domain.channel.entity.Channel;
import com.sds.actlongs.domain.member.entity.Member;
import com.sds.actlongs.domain.video.entity.Video;
import com.sds.actlongs.vo.ImageExtension;
import com.sds.actlongs.vo.VideoExtension;

@Api(tags = "게시글 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/boards")
public class BoardController {

	@ApiOperation(value = "상세 조회 API", notes = "B001: 게시글 상세정보 조회에 성공하였습니다.")
	@GetMapping
	public ResponseEntity<BoardDetailResponse> getBoardDetail(@Valid @RequestBody final BoardRequest request) {
		return ResponseEntity.ok(BoardDetailResponse.of(new Video(
			new Board(
				new Member("harry", null, null),
				new Channel("Knox SRE", new Member("din", null, null), null, null),
				"재진스 플레이리스트",
				"재진스의 뉴진스 플레이리스트 입니다."),
			"static/스크린샷(11)_1686513849288",
			ImageExtension.PNG,
			"data/test_1686534272185",
			VideoExtension.MP4,
			Time.valueOf(LocalTime.now()))));
	}

	@ApiOperation(value = "수정 API", notes = "B002: 게시글 수정에 성공하였습니다.")
	@PatchMapping
	public ResponseEntity<BoardUpdateResponse> updateBoard(@Valid @RequestBody final BoardUpdateRequest request) {
		return ResponseEntity.ok(BoardUpdateResponse.of(new Board(
			new Member("harry", null, null),
			new Channel("Knox SRE", new Member("din", null, null), null, null),
			"재진스(수정)",
			"재진스의 뉴진스 플레이리스트 입니다.(수정)")));
	}

	@ApiOperation(value = "삭제 API", notes = "B003: 게시글 삭제에 성공하였습니다.")
	@DeleteMapping
	public ResponseEntity<BoardDeleteResponse> deleteBoard(@Valid @RequestBody final BoardRequest request) {
		return ResponseEntity.ok(BoardDeleteResponse.of());
	}

}
