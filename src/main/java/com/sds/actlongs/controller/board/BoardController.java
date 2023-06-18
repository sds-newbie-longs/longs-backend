package com.sds.actlongs.controller.board;

import java.sql.Time;
import java.time.LocalTime;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;

import com.sds.actlongs.controller.board.dto.BoardDeleteResponse;
import com.sds.actlongs.controller.board.dto.BoardDetailResponse;
import com.sds.actlongs.controller.board.dto.BoardDto;
import com.sds.actlongs.controller.board.dto.BoardListResponse;
import com.sds.actlongs.controller.board.dto.BoardListSearchResponse;
import com.sds.actlongs.controller.board.dto.BoardRequest;
import com.sds.actlongs.controller.board.dto.BoardUpdateRequest;
import com.sds.actlongs.controller.board.dto.BoardUpdateResponse;
import com.sds.actlongs.controller.board.dto.MemberBoardsDto;
import com.sds.actlongs.domain.board.entity.Board;
import com.sds.actlongs.domain.channel.entity.Channel;
import com.sds.actlongs.domain.member.entity.Member;
import com.sds.actlongs.domain.video.entity.Video;
import com.sds.actlongs.vo.ImageExtension;
import com.sds.actlongs.vo.VideoExtension;

@Api(tags = "게시글 API")
@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/boards")
public class BoardController {

	@ApiOperation(value = "상세 조회 API", notes = "B001: 게시글 상세정보 조회에 성공하였습니다.")
	@GetMapping("/{boardId}")
	public ResponseEntity<BoardDetailResponse> getBoardDetail(
		@PathVariable("boardId") @NotNull @ApiParam(value = "게시글 id", example = "1", required = true) Long boardId) {
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

	@ApiOperation(value = "검색 API", notes = "B004: 게시글 검색에 성공하였습니다.")
	@GetMapping("/groups/{groupId}/search")
	public ResponseEntity<BoardListSearchResponse> searchBoardList(
		@NotNull @ApiParam(value = "그룹 ID", example = "1", required = true)
		@PathVariable("groupId") Long channelId,
		@ApiParam(value = "검색 키워드", example = "재진스", required = true) @Size(max = 50)
		@RequestParam String keyword) {

		Video video1 = new Video(
			new Board(
				new Member("harry", null, null),
				new Channel("Knox SRE", new Member("din", null, null), null, null),
				"재진스",
				"재진스의 뉴진스 플레이리스트 입니다."),
			"static/스크린샷(11)_1686513849288",
			ImageExtension.PNG,
			"data/test_1686534272185",
			VideoExtension.MP4,
			Time.valueOf(LocalTime.now()));
		Video video2 = new Video(
			new Board(
				new Member("ari", null, null),
				new Channel("Knox SRE", new Member("din", null, null), null, null),
				"재진스2",
				"재진스2의 뉴진스 플레이리스트 입니다."),
			"static/스크린샷(11)_1686513849288",
			ImageExtension.PNG,
			"data/test_1686534272185",
			VideoExtension.MP4,
			Time.valueOf(LocalTime.now()));
		List<Video> videoList = List.of(
			video1, video2
		);

		return ResponseEntity.ok(BoardListSearchResponse.of(videoList));
	}

	@ApiOperation(value = "게시글 목록 (메인페이지) API", notes = "B005: 게시글 리스트 조회에 성공하였습니다.")
	@GetMapping("channels/{channelId}")
	public ResponseEntity<BoardListResponse> getBoardList(
		@NotNull @ApiParam(value = "그룹 id", example = "1", required = true)
		@PathVariable("channelId") Long channelId) {
		Video video1 = new Video(
			new Board(
				new Member("harry", null, null),
				new Channel("Knox SRE", new Member("din", null, null), null, null),
				"재진스",
				"재진스의 뉴진스 플레이리스트 입니다."),
			"static/스크린샷(11)_1686513849288",
			ImageExtension.PNG,
			"data/test_1686534272185",
			VideoExtension.MP4,
			Time.valueOf(LocalTime.now()));
		Video video2 = new Video(
			new Board(
				new Member("ari", null, null),
				new Channel("Knox SRE", new Member("din", null, null), null, null),
				"재진스2",
				"재진스2의 뉴진스 플레이리스트 입니다."),
			"static/스크린샷(11)_1686513849288",
			ImageExtension.PNG,
			"data/test_1686534272185",
			VideoExtension.MP4,
			Time.valueOf(LocalTime.now()));
		BoardDto board1 = new BoardDto(video1);
		BoardDto board2 = new BoardDto(video2);

		MemberBoardsDto memberBoard1 = new MemberBoardsDto("harry", List.of(video1));
		MemberBoardsDto memberBoard2 = new MemberBoardsDto("ari", List.of(video2));
		return ResponseEntity.ok(BoardListResponse.of(List.of(board1, board2), List.of(memberBoard1, memberBoard2)));
	}

}
