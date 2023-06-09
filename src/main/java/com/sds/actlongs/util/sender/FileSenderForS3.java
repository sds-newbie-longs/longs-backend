package com.sds.actlongs.util.sender;

import static com.sds.actlongs.util.Constants.*;

import java.io.File;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

import com.sds.actlongs.infra.S3Uploader;
import com.sds.actlongs.util.manage.file.FileManage;

@Component
@RequiredArgsConstructor
@PropertySource("classpath:upload.properties")
public class FileSenderForS3 implements FileSender {

	private final S3Uploader s3Uploader;
	private final FileManage fileManage;

	private static final String VIDEO_START_POINT_IN_S3 = "videos/";
	private static final String THUMBNAIL_START_POINT_IN_S3 = "thumbnails";


	@Value("${temp.video.hls.path}")
	private String hlsPath;
	@Value("${temp.thumbnail.path}")
	private String thumbnailPath;
	@Value("${temp.img.extension}")
	private String thumbnailExtension;

	@Override
	public void sendHlsFiles(String vodUuid) {

		File root = new File(hlsPath + CATEGORY_PREFIX + vodUuid);
		File[] files = root.listFiles();

		String pathInS3 = VIDEO_START_POINT_IN_S3 + vodUuid;

		if (files == null) {
			return;
		}

		this.process(pathInS3, files);

		for (File file : files) {
			if (file.isDirectory()) {
				File child = new File(file.getPath());
				File[] childFiles = child.listFiles();
				if (childFiles != null) {
					process(pathInS3 + CATEGORY_PREFIX + file.getName(), childFiles);
				}
			}
		}
	}

	@Override
	public void sendThumbnailFile(String thumbnailUuid) {
		File thumbnail = new File(thumbnailPath + CATEGORY_PREFIX + thumbnailUuid + thumbnailExtension);
		s3Uploader.uploadFile(fileManage.transFileToMultipartFile(thumbnail), THUMBNAIL_START_POINT_IN_S3);
	}

	private void process(String rooPath, File[] files) {
		for (File file : files) {
			if (file.isFile()) {
				s3Uploader.uploadFile(fileManage.transFileToMultipartFile(file), rooPath);
			}
		}
	}

}
