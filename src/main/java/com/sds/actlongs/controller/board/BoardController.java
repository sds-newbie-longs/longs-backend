package com.sds.actlongs.controller.board;

import static com.sds.actlongs.util.SessionConstants.*;

import java.sql.Time;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

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
import org.springframework.web.bind.annotation.SessionAttribute;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;

import com.sds.actlongs.controller.board.dto.BoardDeleteResponse;
import com.sds.actlongs.controller.board.dto.BoardDetailResponse;
import com.sds.actlongs.controller.board.dto.BoardDto;
import com.sds.actlongs.controller.board.dto.BoardListResponse;
import com.sds.actlongs.controller.board.dto.BoardListSearchResponse;
import com.sds.actlongs.controller.board.dto.BoardUpdateRequest;
import com.sds.actlongs.controller.board.dto.BoardUpdateResponse;
import com.sds.actlongs.controller.board.dto.MemberBoardsDto;
import com.sds.actlongs.domain.board.entity.Board;
import com.sds.actlongs.domain.channel.entity.Channel;
import com.sds.actlongs.domain.member.entity.Member;
import com.sds.actlongs.domain.video.entity.Video;
import com.sds.actlongs.model.Authentication;
import com.sds.actlongs.service.board.BoardService;
import com.sds.actlongs.vo.ImageExtension;
import com.sds.actlongs.vo.VideoExtension;

@Api(tags = "게시글 API")
@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/groups/{groupId}/boards")
public class BoardController {

	private final BoardService boardService;

	@ApiOperation(value = "상세 조회 API", notes = "B001: 게시글 상세정보 조회에 성공하였습니다.\n"
		+ "BOO6: 게시글 상세정보 조회에 실패하였습니다.")
	@GetMapping("/{boardId}")
	public ResponseEntity<BoardDetailResponse> getBoardDetail(
		@PathVariable("groupId") @NotNull @ApiParam(value = "그룹 ID", example = "1", required = true) Long channelId,
		@PathVariable("boardId") @NotNull @ApiParam(value = "게시글 id", example = "1", required = true) Long boardId) {
		Optional<Video> result = boardService.getBoardDetail(boardId);
		return ResponseEntity.ok(BoardDetailResponse.of(result));
	}

	@ApiOperation(value = "수정 API", notes = "B002: 게시글 수정에 성공하였습니다.\n"
		+ "B007: 게시글 수정에 실패하였습니다.")
	@PatchMapping("/{boardId}")
	public ResponseEntity<BoardUpdateResponse> updateBoard(
		@PathVariable("groupId") @NotNull @ApiParam(value = "그룹 ID", example = "1", required = true) Long channelId,
		@PathVariable("boardId") @NotNull @ApiParam(value = "게시글 id", example = "1", required = true) Long boardId,
		@Valid @RequestBody final BoardUpdateRequest request,
		@SessionAttribute(AUTHENTICATION) Authentication authentication) {
		Board updateBoard = new Board(boardId, request.getTitle(), request.getDescription());
		Board result = boardService.updateBoard(updateBoard, authentication.getMemberId());
		return ResponseEntity.ok(BoardUpdateResponse.of(result));
	}

	@ApiOperation(value = "삭제 API", notes = "B003: 게시글 삭제에 성공하였습니다.\n"
		+ "B008: 게시글 삭제에 실패하였습니다.")
	@DeleteMapping("/{boardId}")
	public ResponseEntity<BoardDeleteResponse> deleteBoard(
		@PathVariable("groupId") @NotNull @ApiParam(value = "그룹 ID", example = "1", required = true) Long channelId,
		@PathVariable("boardId") @NotNull @ApiParam(value = "게시글 id", example = "1", required = true) Long boardId,
		@SessionAttribute(AUTHENTICATION) Authentication authentication) {
		boolean result = boardService.deleteBoard(boardId, authentication.getMemberId());
		return ResponseEntity.ok(BoardDeleteResponse.of(result));
	}

	@ApiOperation(value = "제목 검색 API", notes = "B004: 게시글 검색에 성공하였습니다.")
	@GetMapping("/search")
	public ResponseEntity<BoardListSearchResponse> searchBoardList(
		@PathVariable("groupId") @NotNull @ApiParam(value = "그룹 ID", example = "1", required = true) Long channelId,
		@ApiParam(value = "검색 키워드", example = "재진스", required = true) @Size(max = 50)
		@RequestParam String keyword) {
		final List<Video> videos = boardService.searchBoardsIncludeKeywordByChannelId(channelId, keyword);
		return ResponseEntity.ok(BoardListSearchResponse.of(videos));
	}

	@ApiOperation(value = "게시글 목록 (메인페이지) API", notes = "B005: 게시글 리스트 조회에 성공하였습니다.")
	@GetMapping("/boardList")
	public ResponseEntity<BoardListResponse> getBoardList(
		@PathVariable("groupId") @NotNull @ApiParam(value = "그룹 ID", example = "1", required = true) Long channelId) {
		List<BoardDto> boardList = boardService.getBoardList(channelId);
		List<MemberBoardsDto> memberBoardsList = boardService.getMemberBoardsList(channelId);
		return ResponseEntity.ok(BoardListResponse.of(boardList, memberBoardsList));
	}

}
