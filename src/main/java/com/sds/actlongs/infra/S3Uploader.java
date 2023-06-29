package com.sds.actlongs.infra;

import static com.sds.actlongs.util.Constants.*;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class S3Uploader {

	private final AmazonS3 amazonS3;

	@Value("${cloud.aws.s3.bucket}")
	private String bucket;

	public void uploadFile(MultipartFile multipartFile, String path) {
		final long start = System.currentTimeMillis();
		final String fileName = path + CATEGORY_PREFIX + multipartFile.getOriginalFilename();
		log.info("[UPLOAD TO S3] START - {}.", fileName);
		validateFileExists(multipartFile);
		final ObjectMetadata objectMetadata = new ObjectMetadata();
		objectMetadata.setContentLength(multipartFile.getSize());
		objectMetadata.setContentType(multipartFile.getContentType());
		try (InputStream inputStream = multipartFile.getInputStream()) {
			amazonS3.putObject(new PutObjectRequest(bucket, fileName, inputStream, objectMetadata)
					.withCannedAcl(CannedAccessControlList.PublicRead));
		} catch (IOException e) {
			log.error("S3 upload fail : File is not Exist");
			throw new ResponseStatusException(HttpStatus.OK, "파일 업로드에 실패했습니다.");
		} finally {
			final long end = System.currentTimeMillis();
			log.info("[UPLOAD TO S3] END - {}. ({}ms)", fileName, end - start);
		}
	}

	public void deleteFile(String fileName) {
		boolean isFileExist = amazonS3.doesObjectExist(bucket, fileName);
		if (isFileExist) {
			amazonS3.deleteObject(bucket, fileName);
		} else {
			throw new RuntimeException("파일이 존재하지 않습니다.");
		}
	}

	private void validateFileExists(MultipartFile multipartFile) {
		if (multipartFile.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "업로드할 파일이 존재하지 않습니다.");
		}
	}

}
