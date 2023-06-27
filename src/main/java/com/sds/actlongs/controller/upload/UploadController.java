package com.sds.actlongs.controller.upload;

import static com.sds.actlongs.util.SessionConstants.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

import com.sds.actlongs.model.Authentication;
import com.sds.actlongs.service.upload.UploadService;

@Profile({"local", "dev"})
@Api(tags = "동영상 업로드 Api")
@RestController
@RequestMapping("/groups/{groupId}")
@RequiredArgsConstructor
public class UploadController {

	private final UploadService uploadService;

	@ApiOperation(value = "동영상 업로드 엔드포인트", notes = "동영상 업로드 성공시 동영상의 UUID response")
	@RequestMapping(value = {"/video/upload", "/video/upload/**"})
	public ResponseEntity<String> tusUpload(@PathVariable Long groupId,
		@SessionAttribute(AUTHENTICATION) Authentication authentication,
		HttpServletRequest request, HttpServletResponse response) {
		return ResponseEntity.status(HttpStatus.OK)
			.body(uploadService.upload(authentication, groupId, request, response).getVideoUUid());
	}

}
