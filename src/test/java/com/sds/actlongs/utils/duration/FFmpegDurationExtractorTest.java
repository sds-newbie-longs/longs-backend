package com.sds.actlongs.utils.duration;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class FFmpegDurationExtractorTest {

	@Autowired
	DurationExtractor durationExtractor;

	private final Double sampleVideoDuration = 28.236542;
	private final String sampleVideoPath = "./src/test/resources/sample_1920x1080.mp4";

	@Test
	@DisplayName(value = "동영상이 주어주면 동영상의 길이를 추출한다")
	void extract() {
		//given
		//when
		Double result = durationExtractor.extract(sampleVideoPath);
		//then
		Assertions.assertThat(result).isEqualTo(sampleVideoDuration);
	}

}
