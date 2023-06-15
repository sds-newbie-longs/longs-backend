package com.sds.actlongs.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import me.desair.tus.server.TusFileUploadService;

@Configuration
@PropertySource("classpath:upload.properties")
public class TusConfig {

	@Value("${temp.chunk.path}")
	private String dataPath;

	@Value("${temp.chunk.expiration}")
	Long dataExpiration;

	@Bean
	public TusFileUploadService tus() {
		return new TusFileUploadService()
			.withStoragePath(dataPath)
			.withDownloadFeature()
			.withUploadExpirationPeriod(dataExpiration)
			.withThreadLocalCache(true)
			.withUploadURI("/video/upload");
	}
}
