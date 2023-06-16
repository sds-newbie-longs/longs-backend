package com.sds.actlongs.controller.board;

import java.sql.Time;
import java.time.LocalTime;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Size;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;

import com.sds.actlongs.controller.board.dto.BoardListResponse;
import com.sds.actlongs.domain.board.entity.Board;
import com.sds.actlongs.domain.channel.entity.Channel;
import com.sds.actlongs.domain.member.entity.Member;
import com.sds.actlongs.domain.video.entity.Video;
import com.sds.actlongs.vo.ImageExtension;
import com.sds.actlongs.vo.VideoExtension;

@Api(tags = "게시글 검색 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/search")
public class SearchController {

	@ApiOperation(value = "검색 API", notes = "B004: 게시글 검색에 성공하였습니다.")
	@GetMapping
	public ResponseEntity<BoardListResponse> searchBoardList(
		@Valid @ApiParam(value = "그룹 ID", example = "1", required = true)
		@RequestParam Long channelId,
		@Valid @ApiParam(value = "검색 키워드", example = "재진스", required = true) @Size(max = 50)
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

		return ResponseEntity.ok(BoardListResponse.of(videoList));
	}

}
