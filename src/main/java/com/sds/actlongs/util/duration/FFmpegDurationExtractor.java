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

	private final FFprobe fFprobe;

	private final Integer startPoint = 0;

	@Override
	public Double extract(String sourcePath) {
		try {
			FFmpegProbeResult probeResult = fFprobe.probe(sourcePath);
			FFmpegStream videoStream = probeResult.getStreams().get(startPoint);
			return videoStream.duration;
		} catch (IOException exception) {
			//TODO THROW EXCEPTION
		}
		return 0.0;
	}
}
