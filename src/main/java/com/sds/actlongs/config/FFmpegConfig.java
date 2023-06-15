package com.sds.actlongs.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.FFprobe;

@Profile({"local", "dev"})
@Configuration
public class FFmpegConfig {
	@Value("${ffmpeg.path}")
	private String ffmpegPath;

	@Value("${ffprobe.path}")
	private String ffprobePath;

	@Bean
	public FFmpeg ffMpeg() throws IOException {
		return new FFmpeg(ffmpegPath);
	}

	@Bean
	public FFprobe ffProbe() throws IOException {
		return new FFprobe(ffprobePath);
	}
}
