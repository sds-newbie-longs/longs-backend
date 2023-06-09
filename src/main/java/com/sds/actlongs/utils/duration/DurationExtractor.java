package com.sds.actlongs.utils.duration;

import java.io.File;
import java.io.IOException;

public interface DurationExtractor {
	Double extract(File source) throws IOException;
}
