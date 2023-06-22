package com.sds.actlongs.util.duration;

import static com.sds.actlongs.util.Constants.*;

import java.io.IOException;
import java.sql.Time;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import net.bramp.ffmpeg.FFprobe;
import net.bramp.ffmpeg.probe.FFmpegProbeResult;
import net.bramp.ffmpeg.probe.FFmpegStream;

import lombok.RequiredArgsConstructor;

import com.sds.actlongs.util.TimeUtils;

@Profile({"local", "dev"})
@Component
@RequiredArgsConstructor
public class FFmpegDurationExtractor implements DurationExtractor {

	private static final Integer START_POINT = 0;
	private final FFprobe fFprobe;
	@Value("${temp.video.original.path}")
	private String videoOriginalPath;
	@Value("${temp.video.extension}")
	private String videoExtension;

	@Override
	public Double extract(String fileName) {
		String videoPath = videoOriginalPath + CATEGORY_PREFIX + fileName + videoExtension;
		try {
			FFmpegProbeResult probeResult = fFprobe.probe(videoPath);
			FFmpegStream videoStream = probeResult.getStreams().get(START_POINT);
			return videoStream.duration;
		} catch (IOException exception) {
			//TODO THROW EXCEPTION

		}
		return 0.0; // 예외 상황 발생 시 반환하는 default value
	}

	@Override
	public Time extractReturnTime(String fileName) {
		Double extract = this.extract(fileName);
		System.out.println("extract 1");
		Time time = TimeUtils.transDurationToTime(extract);
		System.out.println("extract 1" + time.toString());
		return time;
	}

}
