package com.sds.actlongs.advice;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

import com.sds.actlongs.util.TimeUtils;

@Slf4j
@Aspect
@Component
public class ExecutionTimeAspect {

	@Around("@annotation(com.sds.actlongs.annotation.MeasureExecutionTime)")
	public Object measureExecutionTime(final ProceedingJoinPoint joinPoint) throws Throwable {
		log.debug("Start to measure execution time of {}", joinPoint.toString());
		final long start = System.currentTimeMillis();
		try {
			return joinPoint.proceed();
		} finally {
			final long finish = System.currentTimeMillis();
			final long timeMilliseconds = finish - start;
			log.debug("Execution time of {}: {}", joinPoint.getSignature(),
				TimeUtils.formatMilliseconds(timeMilliseconds));
		}
	}

}
