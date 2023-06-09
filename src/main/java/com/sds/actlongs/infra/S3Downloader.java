package com.sds.actlongs.infra;

import static com.sds.actlongs.util.Constants.*;

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

	public void downloadFile(String fileName, String path) throws IOException {
		validateFileExistsAtUrl(fileName);

		S3Object s3Object = amazonS3.getObject(new GetObjectRequest(bucket, fileName));

		try {
			InputStream in = s3Object.getObjectContent();
			File outputFile = new File(path + CATEGORY_PREFIX + fileName);
			if (!Files.exists(outputFile.toPath().getParent())) {
				Files.createDirectories(outputFile.toPath().getParent());
			}
			Files.copy(in, outputFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "파일 다운로드에 실패했습니다.");
		}
	}

	private void validateFileExistsAtUrl(String fileName) throws FileNotFoundException {
		if (!amazonS3.doesObjectExist(bucket, fileName)) {
			throw new FileNotFoundException();
		}
	}

}
