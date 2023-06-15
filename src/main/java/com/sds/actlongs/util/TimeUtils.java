package com.sds.actlongs.util;

import static com.sds.actlongs.util.Constants.*;

import java.util.concurrent.TimeUnit;

public class TimeUtils {

	private static final String MILLISECONDS_UNIT = "ms";
	private static final String SECONDS_UNIT = "s";
	private static final String MINUTES_UNIT = "m";
	private static final String HOURS_UNIT = "h";
	private static final int SECOND = 1000;
	private static final int SECONDS_PER_MINUTE = 60;
	private static final int MINUTE = SECOND * SECONDS_PER_MINUTE;
	private static final int HOUR = MINUTE * SECONDS_PER_MINUTE;

	public static String formatMilliseconds(final long milliseconds) {
		if (milliseconds < SECOND) {
			return milliseconds + MILLISECONDS_UNIT;
		} else if (milliseconds < MINUTE) {
			final long seconds = TimeUnit.MILLISECONDS.toSeconds(milliseconds);
			final long remainingMilliseconds = milliseconds % SECOND;
			return seconds + SECONDS_UNIT + SPACE + remainingMilliseconds + MILLISECONDS_UNIT;
		} else if (milliseconds < HOUR) {
			final long minutes = TimeUnit.MILLISECONDS.toMinutes(milliseconds);
			final long remainingSeconds = TimeUnit.MILLISECONDS.toSeconds(milliseconds) % SECONDS_PER_MINUTE;
			final long remainingMilliseconds = milliseconds % SECOND;
			return minutes + MINUTES_UNIT + SPACE + remainingSeconds + SECONDS_UNIT + SPACE + remainingMilliseconds
				+ MILLISECONDS_UNIT;
		} else {
			final long hours = TimeUnit.MILLISECONDS.toHours(milliseconds);
			final long remainingMinutes = TimeUnit.MILLISECONDS.toMinutes(milliseconds) % SECONDS_PER_MINUTE;
			final long remainingSeconds = TimeUnit.MILLISECONDS.toSeconds(milliseconds) % SECONDS_PER_MINUTE;
			final long remainingMilliseconds = milliseconds % SECOND;
			return hours + HOURS_UNIT + SPACE + remainingMinutes + MINUTES_UNIT + SPACE + remainingSeconds
				+ SECONDS_UNIT + SPACE + remainingMilliseconds + MILLISECONDS_UNIT;
		}
	}

}
