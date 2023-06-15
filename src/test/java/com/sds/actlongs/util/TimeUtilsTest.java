package com.sds.actlongs.util;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class TimeUtilsTest {

	@Nested
	class FormatMilliseconds {

		@Test
		void ifLessThanSecondThenReturnMillisecondsUnit() {
			// given
			final long milliseconds = 999;
			final String expected = "999ms";

			// when
			final String result = TimeUtils.formatMilliseconds(milliseconds);

			// then
			assertThat(result).isEqualTo(expected);
		}

		@Test
		void ifLessThanMinuteThenReturnSecondsUnit() {
			// given
			final long milliseconds = 999 * 60;
			final String expected = "59s 940ms";

			// when
			final String result = TimeUtils.formatMilliseconds(milliseconds);

			// then
			assertThat(result).isEqualTo(expected);
		}

		@Test
		void ifLessThanHourThenReturnMinutesUnit() {
			// given
			final long milliseconds = 999 * 60 * 60;
			final String expected = "59m 56s 400ms";

			// when
			final String result = TimeUtils.formatMilliseconds(milliseconds);

			// then
			assertThat(result).isEqualTo(expected);
		}

		@Test
		void ifMoreThanHourThenReturnHoursUnit() {
			// given
			final long milliseconds = 1000 * 60 * 60 + 1;
			final String expected = "1h 0m 0s 1ms";

			// when
			final String result = TimeUtils.formatMilliseconds(milliseconds);

			// then
			assertThat(result).isEqualTo(expected);
		}

	}

}
