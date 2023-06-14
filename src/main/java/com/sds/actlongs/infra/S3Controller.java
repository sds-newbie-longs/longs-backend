package com.sds.actlongs.infra;

import java.io.IOException;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class S3Controller {

	@Resource
	S3Downloader s3Downloader;

	@GetMapping("/download")
	public String downloadFile(@RequestParam("resourcePath") String resourcePath) throws
		IOException {
		s3Downloader.downloadFile(resourcePath, "C:/workspace/longs/longs-backend/video/streaming/temp");
		return "ok";
	}

}
