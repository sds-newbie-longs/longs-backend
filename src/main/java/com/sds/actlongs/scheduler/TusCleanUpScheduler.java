package com.sds.actlongs.scheduler;

import java.io.IOException;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import me.desair.tus.server.TusFileUploadService;

@Component
@RequiredArgsConstructor
public class TusCleanUpScheduler {

	private final TusFileUploadService tus;

	@Scheduled(fixedDelay = 10000)
	public void cleanUp() throws IOException {
		tus.cleanup();
	}

}
