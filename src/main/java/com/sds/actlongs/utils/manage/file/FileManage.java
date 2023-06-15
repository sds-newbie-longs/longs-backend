package com.sds.actlongs.utils.manage.file;

import java.io.InputStream;
import java.nio.file.Path;
import java.util.List;

public interface FileManage {
	String createTempVideoFileInLocal(InputStream input, String fileName);

	List<Path> createDirectoryForConvertedVideo(String fileName);
}
