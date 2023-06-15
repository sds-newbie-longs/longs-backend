package com.sds.actlongs.utils.thumbnail;

import java.io.File;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class FFmpegThumbnailExtractorTest {

	@Autowired
	FFmpegThumbnailExtractor fFmpegThumbnailExtractor;

	private final String sampleVideoUuid = "3cc20d2df4bf4ef1b2fa6ee84913716f";
	private final String sampleVideoUuidNotExist = "2a2aa3fa035f44fc93aa9019d0e932cb80";

	@BeforeEach
	void rollback() {
		String imgPath = "./video/upload/temp/img";
		File savedImgFromVideo = new File(imgPath + "/" + sampleVideoUuid + ".png");
		File savedImgFromDefault = new File(imgPath + "/" + sampleVideoUuidNotExist + ".png");

		if (savedImgFromVideo.exists()) {
			savedImgFromVideo.delete();
		}
		if (savedImgFromDefault.exists()) {
			savedImgFromDefault.delete();
		}
	}

	@Test
	@DisplayName(value = "동영상이 있을때 썸네일 뽑아서 동영상 이름과 같은 사진을 저장해야한다")
	void extractWhenVideoExist() {
		//given
		//when
		String savedImgPath = fFmpegThumbnailExtractor.extract(sampleVideoUuid);
		File thumbNailImg = new File(savedImgPath);
		//then
		Assertions.assertThat(thumbNailImg.exists()).isTrue();
	}

	@Test
	@DisplayName(value = "동영상이 없을때 default 이미지를 썸네일로 저장한다")
	void extractWhenVideoNotExist() {
		//given
		//when
		String savedImgPath = fFmpegThumbnailExtractor.extract(sampleVideoUuidNotExist);
		File thumbNailImg = new File(savedImgPath);
		//then
		Assertions.assertThat(thumbNailImg.exists()).isTrue();
	}

	@Test
	@DisplayName(value = "호출하게 되면 default 이미지를 복사하여 새로 생성한다.")
	void generateDefaultThumbnail() {
		// given
		// when
		String savedImgPath = fFmpegThumbnailExtractor.generateDefaultThumbnail(sampleVideoUuid);
		File thumbNailImg = new File(savedImgPath);
		//then
		Assertions.assertThat(thumbNailImg.exists()).isTrue();
	}
}
