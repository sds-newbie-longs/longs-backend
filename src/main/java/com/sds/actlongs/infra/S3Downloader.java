package com.sds.actlongs.infra;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class S3Downloader {

	private final AmazonS3 amazonS3;

	@Value("${cloud.aws.s3.bucket}")
	private String bucket;

	public void downloadFile(String keyName, String path) throws IOException {
		validateFileExistsAtUrl(keyName);

		S3Object s3Object = amazonS3.getObject(new GetObjectRequest(bucket, keyName));

		try {
			InputStream in = s3Object.getObjectContent();
			File outputFile = new File(path + "/" + keyName);
			if (!Files.exists(outputFile.toPath().getParent())) {
				Files.createDirectories(outputFile.toPath().getParent());
			}
			Files.copy(in, outputFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "파일 다운로드에 실패했습니다.");
		}
	}

	private void validateFileExistsAtUrl(String filePath) throws FileNotFoundException {
		if (!amazonS3.doesObjectExist(bucket, filePath)) {
			throw new FileNotFoundException();
		}
	}

}
