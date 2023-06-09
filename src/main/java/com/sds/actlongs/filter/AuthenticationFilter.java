package com.sds.actlongs.filter;

import static com.sds.actlongs.model.ErrorCode.*;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.stream.Stream;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.http.MediaType;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.cors.CorsUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.sds.actlongs.model.ErrorResponse;
import com.sds.actlongs.util.SessionConstants;

public class AuthenticationFilter implements Filter {

	private static final String[] API_WHITELIST = {"/members/login"};
	private static final String[] SWAGGER_WHITELIST = {"/v2/api-docs/**", "/configuration/ui/**",
		"/swagger-resources/**", "/configuration/security/**", "/swagger-ui.html/**", "/webjars/**", "/swagger/**",
		"/swagger-ui/**", "/actuator/**"};
	private final ObjectMapper objectMapper = new ObjectMapper();
	private final AntPathMatcher antPathMatcher = new AntPathMatcher();

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
		throws ServletException, IOException {
		if (processAuthenticationAndGetResult((HttpServletRequest)request, (HttpServletResponse)response)) {
			chain.doFilter(request, response);
		}
	}

	private boolean processAuthenticationAndGetResult(HttpServletRequest httpRequest,
		HttpServletResponse httpResponse) {
		if (CorsUtils.isPreFlightRequest(httpRequest)) {
			return true;
		} else if (isAuthenticationPath(httpRequest.getRequestURI())
			&& isSessionExpiredOrInvalid(httpRequest.getSession(false))) {
			handleAuthenticationFailure(httpResponse);
			return false;
		}
		return true;
	}

	private boolean isSessionExpiredOrInvalid(HttpSession session) {
		return session == null || session.getAttribute(SessionConstants.AUTHENTICATION) == null;
	}

	private void handleAuthenticationFailure(HttpServletResponse httpResponse) {
		httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		httpResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
		try (OutputStream os = httpResponse.getOutputStream()) {
			objectMapper.writeValue(os, ErrorResponse.of(AUTHENTICATION_FAILURE));
			os.flush();
		} catch (Exception exception) {
			exception.printStackTrace();
			throw new RuntimeException(exception.getMessage(), exception.getCause());
		}
	}

	private boolean isAuthenticationPath(String requestUri) {
		return getWhitelistStream().noneMatch(uri -> antPathMatcher.match(uri, requestUri));
	}

	private Stream<String> getWhitelistStream() {
		return Stream.concat(
			Arrays.stream(API_WHITELIST),
			Arrays.stream(SWAGGER_WHITELIST));
	}

}
