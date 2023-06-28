package com.sds.actlongs.advice;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Aspect
@Component
public class ExecutionTimeAspect {

	@Around("@annotation(com.sds.actlongs.annotation.MeasureExecutionTime)")
	public Object measureExecutionTime(final ProceedingJoinPoint joinPoint) throws Throwable {
		final long start = System.currentTimeMillis();
		final HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes())
			.getRequest();
		final String endpoint = request.getRequestURI();
		final String httpMethod = request.getMethod();
		try {
			return joinPoint.proceed();
		} finally {
			final long finish = System.currentTimeMillis();
			final long timeMilliseconds = finish - start;
			log.info("[EXECUTION_TIME][{}][{}]: {}ms.", endpoint, httpMethod, timeMilliseconds);
		}
	}

}
