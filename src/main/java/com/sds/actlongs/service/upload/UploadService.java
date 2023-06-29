package com.sds.actlongs.service.upload;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sds.actlongs.controller.upload.dto.UploadVideoRequestDto;
import com.sds.actlongs.model.Authentication;
import com.sds.actlongs.model.ResultCode;
import com.sds.actlongs.service.upload.dto.UploadResponseDto;

public interface UploadService {

	UploadResponseDto upload(Authentication authentication, Long groupId, HttpServletRequest request,
		HttpServletResponse response);

	ResultCode uploadVideoToS3(Long id, UploadVideoRequestDto requestDto);

}
