package com.sds.actlongs.utils.thumbnail;

import java.io.File;
import java.io.IOException;

public interface ThumbnailExtractor {

	String extract(File source) throws IOException;
	String generateDefaultThumbnail(File source) throws IOException;
}
