package com.sds.actlongs.advice;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.util.List;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;

@ExtendWith(MockitoExtension.class)
class ExecutionTimeAspectTest {

	@Mock
	private ProceedingJoinPoint joinPoint;

	private Logger logger = (Logger)LoggerFactory.getLogger(ExecutionTimeAspect.class);

	@InjectMocks
	private ExecutionTimeAspect subject;

	@Nested
	class MeasureExecutionTime {

		@Test
		@DisplayName("메소드의 실행 시간을 Debug level log로 출력한다.")
		void measureExecutionTime() throws Throwable {
			// given
			final MethodSignature methodSignature = mock(MethodSignature.class);
			given(joinPoint.getSignature()).willReturn(methodSignature);

			final ListAppender<ILoggingEvent> listAppender = new ListAppender<>();
			listAppender.start();
			logger.addAppender(listAppender);
			final List<ILoggingEvent> loggingEvents = listAppender.list;

			// when
			subject.measureExecutionTime(joinPoint);

			// then
			then(joinPoint).should(times(1)).proceed();
			assertThat("DEBUG").isEqualTo(loggingEvents.get(0).getLevel().toString());
			assertThat("DEBUG").isEqualTo(loggingEvents.get(1).getLevel().toString());
			assertThat("Start to measure execution time of {}").isEqualTo(loggingEvents.get(0).getMessage());
			assertThat("Execution time of {}: {}").isEqualTo(loggingEvents.get(1).getMessage());
		}

	}

}
