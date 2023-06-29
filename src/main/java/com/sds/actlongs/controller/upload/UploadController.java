package com.sds.actlongs.controller.upload;

import static com.sds.actlongs.util.SessionConstants.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

import com.sds.actlongs.controller.board.dto.BoardCreateRequest;
import com.sds.actlongs.controller.board.dto.BoardCreateResponse;
import com.sds.actlongs.controller.upload.dto.UploadVideoRequestDto;
import com.sds.actlongs.model.Authentication;
import com.sds.actlongs.model.ResultCode;
import com.sds.actlongs.service.upload.UploadService;

@Profile({"local", "dev"})
@Api(tags = "동영상 업로드 Api")
@RestController
@RequiredArgsConstructor
public class UploadController {

	private final UploadService uploadService;

	// TODO - 인가필터 url 추가
	@ApiOperation(value = "동영상 업로드 엔드포인트", notes = "동영상 업로드 성공시 동영상의 UUID response")
	@RequestMapping(value = {"/groups/{groupId}/video/upload", "/groups/{groupId}/video/upload/**"})
	public ResponseEntity<String> tusUpload(@PathVariable Long groupId,
		@SessionAttribute(AUTHENTICATION) Authentication authentication,
		HttpServletRequest request, HttpServletResponse response) {
		Long boardId = uploadService.upload(authentication, groupId, request, response).getBoardId();
		return ResponseEntity.status(HttpStatus.OK)
			.body(boardId != null ? boardId.toString() : "");
	}

	@ApiOperation(value = "동영상 인코딩 요청", notes = "B006: 게시글 등록에 성공했습니다")
	@PostMapping("/upload/720p")
	public ResponseEntity<BoardCreateResponse> createBoard(@Valid @RequestBody UploadVideoRequestDto request,
		@SessionAttribute("authentication") Authentication authentication) {
		ResultCode result = uploadService.uploadVideoToS3(authentication.getMemberId(), request);

		return (result.getStatus() == HttpStatus.OK.value() ? ResponseEntity.ok() : ResponseEntity.badRequest()).body(
			BoardCreateResponse.of(result));
	}

}
