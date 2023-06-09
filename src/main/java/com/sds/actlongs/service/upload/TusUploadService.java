package com.sds.actlongs.service.upload;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import me.desair.tus.server.TusFileUploadService;

@Service
@RequiredArgsConstructor
public class TusUploadService implements UploadService{

	private final TusFileUploadService tusFileUploadService;

	private String savePath;

	@Override
	public void upload(HttpServletRequest request, HttpServletResponse response) {
		/*
		#TODO
		 1. save file in local
		 2. get UUID
		 3. get thumbnail
		 4. convert to Hls codec
		 5. upload s3
		 6. save video db
		 */
	}

}
