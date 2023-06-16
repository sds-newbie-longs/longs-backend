package com.sds.actlongs.util.manage.file;

import java.io.InputStream;
import java.nio.file.Path;
import java.util.List;

public interface FileManage {
	String createTempVideoFileInLocal(InputStream input, String fileName);

	List<Path> createDirectoryForConvertedVideo(String fileName);
}
