package com.sds.actlongs.utils.thumbnail;

import java.io.File;
import java.io.IOException;

public interface ThumbnailExtractor {

	String extract(String fileName);

	String generateDefaultThumbnail(String fileName);
}
