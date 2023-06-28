package com.sds.actlongs.advice;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ExecutionTimeAspectTest {

	/*@Mock
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

	}*/

}
