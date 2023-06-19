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
import com.sds.actlongs.filter.LoginFilter;

@Configuration
public class WebConfig {

	private final String LOCAL_URL = "http://localhost:3000";
	private final String CLOUDFRONT_URL = "https://longs.iamnew.net";

	@Bean
	@Profile("local")
	public WebMvcConfigurer localCorsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry
					.addMapping(ALL_SUB_PATHS)
					.allowedMethods(CorsConfiguration.ALL)
					.allowedOrigins(LOCAL_URL)
					.allowedHeaders(CorsConfiguration.ALL)
					.allowCredentials(true);
			}
		};
	}

	@Bean
	@Profile("dev")
	public WebMvcConfigurer devCorsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry
					.addMapping(ALL_SUB_PATHS)
					.allowedMethods(CorsConfiguration.ALL)
					.allowedOrigins(LOCAL_URL, CLOUDFRONT_URL)
					.allowedHeaders(CorsConfiguration.ALL)
					.allowCredentials(true);
			}
		};
	}

	@Bean
	public FilterRegistrationBean<Filter> authenticationFilter() {
		final FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<>();
		filterRegistrationBean.setFilter(new AuthenticationFilter());
		filterRegistrationBean.setOrder(2);
		filterRegistrationBean.addUrlPatterns(ALL_PATHS);
		return filterRegistrationBean;
	}

	@Bean
	@Profile("dev")
	public FilterRegistrationBean<Filter> loginFilter() {
		final FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<>();
		filterRegistrationBean.setFilter(new LoginFilter());
		filterRegistrationBean.setOrder(1);
		filterRegistrationBean.addUrlPatterns(API_LOGIN);
		return filterRegistrationBean;
	}

}
