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
}
