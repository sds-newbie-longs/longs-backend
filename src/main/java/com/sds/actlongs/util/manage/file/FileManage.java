package com.sds.actlongs.util.manage.file;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public interface FileManage {

	String createTempVideoFileInLocal(InputStream input, String fileName);

	List<Path> createDirectoryForConvertedVideo(String fileName);

	Boolean checkFileExistVideo(String fileName);

	Boolean checkFileExistImg(String fileName);

	Boolean deleteTempVideo(String fileName);

	Boolean deleteTempImage(String fileName);

	Boolean deleteTempHls(String fileName);

	Boolean deleteFiles(File file);

	MultipartFile transFileToMultipartFile(File input);

	Long getVideoSizeMB(String fileName);

}
