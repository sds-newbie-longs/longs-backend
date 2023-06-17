package com.sds.actlongs.util.duration;

import java.io.IOException;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import net.bramp.ffmpeg.FFprobe;
import net.bramp.ffmpeg.probe.FFmpegProbeResult;
import net.bramp.ffmpeg.probe.FFmpegStream;

import lombok.RequiredArgsConstructor;

@Profile({"local", "dev"})
@Component
@RequiredArgsConstructor
public class FFmpegDurationExtractor implements DurationExtractor {

	private static final Integer START_POINT = 0;
	private final FFprobe fFprobe;

	@Override
	public Double extract(String sourcePath) {
		try {
			FFmpegProbeResult probeResult = fFprobe.probe(sourcePath);
			FFmpegStream videoStream = probeResult.getStreams().get(START_POINT);
			return videoStream.duration;
		} catch (IOException exception) {
			//TODO THROW EXCEPTION
		}
		return 0.0; // 예외 상황 발생 시 반환하는 default value
	}

}
