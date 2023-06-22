package com.sds.actlongs.util.thumbnail;

import static com.sds.actlongs.util.Constants.*;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.builder.FFmpegBuilder;

import lombok.RequiredArgsConstructor;

@Profile({"local", "dev"})
@Component
@RequiredArgsConstructor
@PropertySource("classpath:upload.properties")
public class FFmpegThumbnailExtractor implements ThumbnailExtractor {

	private final FFmpeg ffmpeg;

	@Value("${ffmpeg.thumbnail.extract.frame}")
	private Integer grabFrameNum;

	@Value("${temp.video.original.path}")
	private String originalVideoPath;

	@Value("${temp.thumbnail.path}")
	private String imgSavePath;

	@Value("${temp.img.extension}")
	private String imgExtension;

	@Value("${temp.video.extension}")
	private String videoExtension;

	@Value("${temp.default.thumbnail.path}")
	private String defaultImgPath;

	// TODO - refactor
	@Override
	public String extract(String fileName) {
		String sourcePath = originalVideoPath + CATEGORY_PREFIX + fileName + videoExtension;
		final String outputPath = imgSavePath + CATEGORY_PREFIX + fileName + imgExtension;

		try {
			FFmpegBuilder builder = new FFmpegBuilder()
				.setInput(sourcePath)
				.overrideOutputFiles(true)
				.addOutput(outputPath)
				.setFormat("image2")
				.setFrames(grabFrameNum)
				.setVideoFrameRate(1)
				.done();

			ffmpeg.run(builder);
		} catch (Exception e) {
			generateDefaultThumbnail(fileName);
		}
		return outputPath;
	}

	@Override
	public String generateDefaultThumbnail(String fileName) {
		String outputPath = imgSavePath + CATEGORY_PREFIX + fileName + imgExtension;
		File thumbnail = new File(outputPath);
		File defaultImage = new File(defaultImgPath);

		try {
			FileUtils.copyFile(defaultImage, thumbnail);
		} catch (Exception exception) {
			//TODO THROW EXCEPTION
		}
		return outputPath;
	}

}
