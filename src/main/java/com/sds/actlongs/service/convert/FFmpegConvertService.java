package com.sds.actlongs.service.convert;

import static com.sds.actlongs.util.Constants.*;

import java.nio.file.Path;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.FFmpegExecutor;
import net.bramp.ffmpeg.FFprobe;
import net.bramp.ffmpeg.builder.FFmpegBuilder;
import net.bramp.ffmpeg.builder.FFmpegOutputBuilder;
import net.bramp.ffmpeg.progress.Progress;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import com.sds.actlongs.model.IncodingStatus;
import com.sds.actlongs.util.manage.file.FileManage;

@Slf4j
@Profile({"local", "dev"})
@Service
@RequiredArgsConstructor
@PropertySource("classpath:upload.properties")
public class FFmpegConvertService implements ConvertService {

	private static final String JOB_NAME = "FFMPEG JOB";
	private static final String STATUS_RUN = "RUN";
	private static final String STATUS_FINISH = "FINISH";
	private final FFmpeg fFmpeg;
	private final FFprobe fFprobe;
	private final FileManage fileManage;
	@Value("${ffmpeg.video.chunk.size}")
	private String chunkSize;
	@Value("${ffmpeg.video.ts.default}")
	private String fileFormatPolicy;

	@Override
	public void convertToHlsWithCodec(String fileName, IncodingStatus status) {
		List<Path> paths = fileManage.createDirectoryForConvertedVideo(fileName);
		Path inputFilePath = paths.get(0);
		Path outputFolderPath = paths.get(1);

		FFmpegBuilder builder = new FFmpegBuilder();
		FFmpegOutputBuilder outPutBuilder = initSetting(builder, inputFilePath, outputFolderPath);

		if (status.equals(IncodingStatus.NEED_CODEC_H264)) {
			masterSettingWithCodecH264(outPutBuilder);
		} else if (status.equals(IncodingStatus.NEED_CODEC_VP9)) {
			masterSettingWithCodecVP9(outPutBuilder);
		}
		incodingDefaultSetting(outPutBuilder, outputFolderPath);
		hls_720Setting(outPutBuilder);
		hls_480Setting(outPutBuilder);
		outPutBuilder.done();

		run(builder);
	}

	@Override
	public void convertToHlsWithoutCodec(String fileName) {
		List<Path> paths = fileManage.createDirectoryForConvertedVideo(fileName);
		Path inputFilePath = paths.get(0);
		Path outputFolderPath = paths.get(1);

		FFmpegBuilder builder = new FFmpegBuilder();
		FFmpegOutputBuilder outPutBuilder = initSetting(builder, inputFilePath, outputFolderPath);

		masterSettingWithoutCodec(outPutBuilder);
		incodingDefaultSetting(outPutBuilder, outputFolderPath);
		hls_720Setting(outPutBuilder);
		hls_480Setting(outPutBuilder);
		outPutBuilder.done();

		run(builder);
	}

	private void incodingDefaultSetting(FFmpegOutputBuilder builder, Path outputFolderPath) {
		builder.addExtraArgs("-hls_time", chunkSize)
			.addExtraArgs("-hls_list_size", "0")
			.addExtraArgs("-hls_segment_filename", outputFolderPath.toAbsolutePath() + fileFormatPolicy)
			.addExtraArgs("-master_pl_name", "master.m3u8")
			.addExtraArgs("-map", "0:v")
			.addExtraArgs("-map", "0:a")
			.addExtraArgs("-map", "0:v")
			.addExtraArgs("-map", "0:a")
			.addExtraArgs("-var_stream_map", "v:0,a:0,name:720 v:1,a:1,name:480")
			.addExtraArgs("-vf",
				"scale=1920x1080:force_original_aspect_ratio=decrease,pad=1920:1080:(ow-iw)/2:(oh-ih)/2");
	}

	private FFmpegOutputBuilder initSetting(FFmpegBuilder builder, Path inputFilePath, Path outputFolderPath) {
		return builder.setInput(inputFilePath.toAbsolutePath().toString())
			.addExtraArgs("-y")
			.addOutput(outputFolderPath.toAbsolutePath() + "/%v/playList.m3u8");
	}

	private void masterSettingWithoutCodec(FFmpegOutputBuilder builder) {
		builder.setFormat("hls");
	}

	private void masterSettingWithCodecH264(FFmpegOutputBuilder builder) {
		builder.setFormat("hls")
			.setVideoCodec("libx264");
	}

	private void masterSettingWithCodecVP9(FFmpegOutputBuilder builder) {
		builder.setFormat("hls")
			.setVideoCodec("libvpx-vp9");
	}

	private void hls_1080Setting(FFmpegOutputBuilder builder) {
		builder.addExtraArgs("-b:v:0", "5000k")
			.addExtraArgs("-maxrate:v:0", "5000k")
			.addExtraArgs("-bufsize:v:0", "10000k")
			.addExtraArgs("-s:v:0", "1920x1080")
			.addExtraArgs("-crf:v:0", "17")
			.addExtraArgs("-b:a:0", "128k");
	}

	private void hls_720Setting(FFmpegOutputBuilder builder) {
		builder.addExtraArgs("-b:v:0", "2500k")
			.addExtraArgs("-maxrate:v:0", "2500k")
			.addExtraArgs("-bufsize:v:0", "5000k")
			.addExtraArgs("-s:v:0", "1280x720")
			.addExtraArgs("-crf:v:0", "24")
			.addExtraArgs("-b:a:0", "96k");
	}

	private void hls_480Setting(FFmpegOutputBuilder builder) {
		builder.addExtraArgs("-b:v:1", "1000k")
			.addExtraArgs("-maxrate:v:1", "1000k")
			.addExtraArgs("-bufsize:v:1", "2000k")
			.addExtraArgs("-s:v:1", "854x480")
			.addExtraArgs("-crf:v:1", "30")
			.addExtraArgs("-b:a:1", "64k");
	}

	private void run(FFmpegBuilder builder) {
		FFmpegExecutor executor = new FFmpegExecutor(fFmpeg, fFprobe);
		executor.createJob(builder, progress -> {
			if (progress.status.equals(Progress.Status.END)) {
				log.debug(JOB_NAME + HYPHEN + STATUS_FINISH + SPACE + progress);
			} else if (progress.status.equals(Progress.Status.CONTINUE)) {
				log.debug(JOB_NAME + HYPHEN + STATUS_RUN + SPACE + progress);
			}
		}).run();
	}

}
