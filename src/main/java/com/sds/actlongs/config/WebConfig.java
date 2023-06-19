package com.sds.actlongs.config;

import static com.sds.actlongs.util.Constants.*;

import javax.servlet.Filter;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.sds.actlongs.filter.AuthenticationFilter;

@Configuration
public class WebConfig {

	@Bean
	@Profile({"local", "dev"})
	public WebMvcConfigurer localAndDevCorsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry
					.addMapping(ALL_SUB_PATHS)
					.allowedMethods(CorsConfiguration.ALL)
					.allowedOrigins("http://localhost:3000")
					.allowedHeaders(CorsConfiguration.ALL)
					.allowCredentials(true);
			}
		};
	}

	@Bean
	@Profile({"dev"})
	public FilterRegistrationBean<Filter> authenticationFilter() {
		final FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<>();
		filterRegistrationBean.setFilter(new AuthenticationFilter());
		filterRegistrationBean.setOrder(1);
		filterRegistrationBean.addUrlPatterns(ALL_PATHS);
		return filterRegistrationBean;
	}

}
