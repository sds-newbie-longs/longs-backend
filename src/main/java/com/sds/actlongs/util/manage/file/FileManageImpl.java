package com.sds.actlongs.util.manage.file;

import static com.sds.actlongs.util.Constants.*;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:upload.properties")
public class FileManageImpl implements FileManage {

	@Value("${temp.video.original.path}")
	private String saveOriginalPath;

	@Value("${temp.video.hls.path}")
	private String saveHlsPath;

	@Value("${temp.video.extension}")
	private String videoExtension;

	private static final String HLS_480 = "480";
	private static final String HLS_720 = "720";
	private static final String HLS_1080 = "1080";

	@Override
	public String createTempVideoFileInLocal(InputStream input, String fileName) {
		String uploadPath = saveOriginalPath + CATEGORY_PREFIX;
		try {
			File video = new File(uploadPath + fileName + videoExtension);
			FileUtils.copyInputStreamToFile(input, video);
		} catch (IOException exception) {
			//TODO THROW EXCEPTION
		}
		return uploadPath + fileName + videoExtension;
	}

	@Override
	public List<Path> createDirectoryForConvertedVideo(String fileName) {
		Path inputFilePath = Paths.get(saveOriginalPath + CATEGORY_PREFIX + fileName + videoExtension);
		Path outputFolderPath = Paths.get(saveHlsPath + CATEGORY_PREFIX + fileName);

		File preFix = outputFolderPath.toFile();
		File hls480 = new File(preFix, HLS_480);
		File hls720 = new File(preFix, HLS_720);
		File hls1080 = new File(preFix, HLS_1080);

		if (!hls480.exists()) {
			hls480.mkdirs();
		}
		if (!hls720.exists()) {
			hls720.mkdirs();
		}
		if (!hls1080.exists()) {
			hls1080.mkdirs();
		}

		List<Path> paths = new ArrayList<>();
		paths.add(inputFilePath);
		paths.add(outputFolderPath);
		return paths;
	}

}
