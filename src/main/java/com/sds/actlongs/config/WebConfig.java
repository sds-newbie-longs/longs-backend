package com.sds.actlongs.config;

import static com.sds.actlongs.util.Constants.*;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig {

	@Bean
	@Profile({"local", "dev"})
	public WebMvcConfigurer localCorsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry
					.addMapping(ALL_PATH)
					.allowedMethods(WILDCARD)
					.allowedOrigins("http://localhost:3000")
					.allowedHeaders(WILDCARD)
					.allowCredentials(true);
			}
		};
	}

}
