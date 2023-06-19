package com.sds.actlongs.util.duration;

import java.io.File;
import java.io.IOException;
import java.sql.Time;

public interface DurationExtractor {
	Double extract(String fileName);

	Time extractReturnTime(String fileName);
}
