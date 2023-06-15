package com.sds.actlongs.service.convert;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.sds.actlongs.utils.manage.file.FileManage;
import com.sds.actlongs.utils.uuid.UuidGenerate;

@SpringBootTest
class FFmpegConvertServiceTest {

	private final String hlsFilePath = "./video/temp/hls";
	private final String sampleVideoPath = "./src/test/resources/sample_1920x1080.mp4";
	@Autowired
	FFmpegConvertService convertService;
	@Autowired
	FileManage fileManage;
	@Autowired
	UuidGenerate uuidGenerate;
	private String sampleUuid;

	@BeforeEach
	void init() {
		File sampleVideo = new File(sampleVideoPath);
		InputStream videoInputStream;
		try {
			videoInputStream = new FileInputStream(sampleVideo);
		} catch (IOException e) {
			return;
		}
		sampleUuid = uuidGenerate.getVodUuid();
		fileManage.createTempVideoFileInLocal(videoInputStream, sampleUuid);
	}

	@Test
	@DisplayName(value = "동영상제목을 전달하여 인코딩을 하게되면 hls하위 디렉토리에 각 화질별 비디오 청크 파일이 생성되고 m3u8파일이 생성된다")
	void convertToHls() {
		//given
		//when
		convertService.convertToHls(sampleUuid);
		String sampleHlsPath = hlsFilePath + "/" + sampleUuid;
		File hls = new File(sampleHlsPath);
		File hls480 = new File(sampleHlsPath + "/480");
		File hls720 = new File(sampleHlsPath + "/720");
		File hls1080 = new File(sampleHlsPath + "/1080");
		//then
		Assertions.assertThat(hls.exists()).isTrue();
		Assertions.assertThat(hls480.exists()).isTrue();
		Assertions.assertThat(hls720.exists()).isTrue();
		Assertions.assertThat(hls1080.exists()).isTrue();

		Assertions.assertThat(hls480.listFiles()).isNotNull();
		Assertions.assertThat(hls720.listFiles()).isNotNull();
		Assertions.assertThat(hls1080.listFiles()).isNotNull();
	}

}
