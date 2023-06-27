package com.sds.actlongs.service.upload;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sds.actlongs.model.Authentication;
import com.sds.actlongs.service.upload.dto.UploadResponseDto;

public interface UploadService {

	UploadResponseDto upload(Authentication authentication, Long groupId, HttpServletRequest request,
		HttpServletResponse response);

}
