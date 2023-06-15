package com.sds.actlongs.controller.upload;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

import com.sds.actlongs.service.upload.UploadService;
import com.sds.actlongs.service.upload.dto.UploadResponseDto;

@Api(tags = "동영상 업로드 Api")
@RestController
@RequiredArgsConstructor
public class UploadController {

	private final UploadService uploadService;

	@ApiOperation(value = "동영상 업로드 엔드포인트", notes = "동영상 업로드 성공시 동영상의 UUID response")
	@RequestMapping(value = {"/video/upload", "/video/upload/**"})
	public ResponseEntity<UploadResponseDto> tusUpload(HttpServletRequest request, HttpServletResponse response) {
		return ResponseEntity.status(HttpStatus.OK).body(uploadService.upload(request, response));
	}

}
