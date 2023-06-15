package com.sds.actlongs.service.upload;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sds.actlongs.service.upload.dto.UploadResponseDto;

public interface UploadService {

	UploadResponseDto upload(HttpServletRequest request, HttpServletResponse response);

}
