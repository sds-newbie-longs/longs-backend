package com.sds.actlongs.util.manage.file;

import static com.sds.actlongs.util.Constants.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

@Component
@PropertySource("classpath:upload.properties")
public class FileManageImpl implements FileManage {

	private static final String HLS_480 = "480";
	private static final String HLS_720 = "720";
	private static final String HLS_1080 = "1080";
	@Value("${temp.video.original.path}")
	private String saveOriginalPath;
	@Value("${temp.thumbnail.path}")
	private String saveThumbnailPath;
	@Value("${temp.video.hls.path}")
	private String saveHlsPath;
	@Value("${temp.video.extension}")
	private String videoExtension;
	@Value("${temp.img.extension}")
	private String imgExtension;

	@Override
	public String createTempVideoFileInLocal(InputStream input, String fileName) {
		String uploadPath = saveOriginalPath + CATEGORY_PREFIX;
		try {
			Files.createDirectories(Paths.get(saveOriginalPath));
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

	@Override
	public Boolean checkFileExistVideo(String fileName) {
		String needCheckFile =
			saveOriginalPath + CATEGORY_PREFIX + fileName + videoExtension;

		File file = new File(needCheckFile);
		return file.exists();
	}

	@Override
	public Boolean checkFileExistImg(String fileName) {
		String needCheckFile =
			saveThumbnailPath + CATEGORY_PREFIX + fileName + imgExtension;

		File file = new File(needCheckFile);
		return file.exists();
	}

	@Override
	public Boolean deleteTempVideo(String fileName) {
		File originalVideo = new File(saveOriginalPath + CATEGORY_PREFIX + fileName + videoExtension);
		return originalVideo.delete();
	}

	@Override
	public Boolean deleteTempImage(String fileName) {
		File thumbnail = new File(saveThumbnailPath + CATEGORY_PREFIX + fileName + imgExtension);
		return thumbnail.delete();
	}

	@Override
	public Boolean deleteTempHls(String fileName) {
		File hls = new File(saveHlsPath + CATEGORY_PREFIX + fileName);
		return deleteFiles(hls);
	}

	@Override
	public MultipartFile transFileToMultipartFile(File input) {
		try {
			FileItem fileItem = new DiskFileItem(
				"",
				Files.probeContentType(input.toPath()),
				false,
				input.getName(),
				(int)input.length(),
				input.getParentFile());

			InputStream fileInputStream = new FileInputStream(input);
			OutputStream outputStream = fileItem.getOutputStream();
			IOUtils.copy(fileInputStream, outputStream);

			return new CommonsMultipartFile(fileItem);
		} catch (IOException exception) {

		}
		return null;
	}

	@Override
	public Boolean deleteFiles(File file) {
		if (file.isDirectory()) {
			File[] files = file.listFiles();
			if (files != null) {
				for (File child : files) {
					deleteFiles(child);
				}
			}
		}
		return file.delete();
	}

}
