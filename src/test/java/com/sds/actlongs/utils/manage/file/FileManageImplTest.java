package com.sds.actlongs.utils.manage.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.sds.actlongs.utils.uuid.UuidGenerate;

@SpringBootTest
class FileManageImplTest {

	private final String sampleVideoPath = "./src/test/resources/sample_1920x1080.mp4";
	@Autowired
	FileManage fileManage;
	@Autowired
	UuidGenerate uuidGenerate;
	private File sampleVide;

	@BeforeEach
	void init() {
		sampleVide = new File(sampleVideoPath);
	}

	@Test
	@DisplayName(value = "InputStream 형식의 Video데이터를 mp4파일로 생성되어야 한다")
	void saveMp4FileUsingInputStream() {
		//given
		InputStream videoInputStream;
		try {
			videoInputStream = new FileInputStream(sampleVide);
		} catch (IOException e) {
			return;
		}
		//when
		String testOriginalVideoFilePath = fileManage.createTempVideoFileInLocal(videoInputStream,
			uuidGenerate.getVodUuid());
		File testOriginalVideo = new File(testOriginalVideoFilePath);
		//then
		Assertions.assertThat(testOriginalVideo.exists()).isTrue();
	}

	@Test
	@DisplayName(value = "인코딩된 파일들을 저장하기위해 video/temp/hls하위에 디렉토리 생성이 되어야한다")
	void createDirectoryForConvertedVideo() {
		//given
		String vodName = uuidGenerate.getVodUuid();
		//when
		Path outPutPath = fileManage.createDirectoryForConvertedVideo(vodName).get(1);
		File root = outPutPath.toFile();
		File hls480 = new File(root, "480");
		File hls720 = new File(root, "720");
		File hls1080 = new File(root, "1080");
		//then
		Assertions.assertThat(hls480.exists()).isTrue();
		Assertions.assertThat(hls720.exists()).isTrue();
		Assertions.assertThat(hls1080.exists()).isTrue();
	}

}
