package com.sds.actlongs.controller.upload;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

import com.sds.actlongs.service.upload.UploadService;
import com.sds.actlongs.service.upload.dto.UploadResponseDto;

@RestController
@RequiredArgsConstructor
public class UploadController {
	private final UploadService uploadService;

	@RequestMapping(value = {"/video/upload", "/video/upload/**"})
	public ResponseEntity<UploadResponseDto> tusUpload(HttpServletRequest request, HttpServletResponse response) {
		return ResponseEntity.status(HttpStatus.OK).body(uploadService.upload(request, response));
	}
}
