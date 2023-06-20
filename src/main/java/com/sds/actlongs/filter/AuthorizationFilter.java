package com.sds.actlongs.filter;

import static com.sds.actlongs.model.ErrorCode.*;
import static com.sds.actlongs.util.SessionConstants.*;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.http.MediaType;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Getter;

import com.sds.actlongs.model.Authentication;
import com.sds.actlongs.model.Authentication.ChannelRoles;
import com.sds.actlongs.model.ErrorResponse;

public class AuthorizationFilter implements Filter {

	private static final String PATH_VARIABLE = "/\\d+";
	private static final List<HttpRequest> API_AUTHORIZATION_OF_MEMBER_LIST = List.of(
		HttpRequest.of("/groups" + PATH_VARIABLE + "/boards" + PATH_VARIABLE, "GET"),
		HttpRequest.of("/groups" + PATH_VARIABLE + "/boards" + PATH_VARIABLE, "PATCH"),
		HttpRequest.of("/groups" + PATH_VARIABLE + "/boards" + PATH_VARIABLE, "DELETE"),
		HttpRequest.of("/groups" + PATH_VARIABLE + "/boards", "POST"),
		HttpRequest.of("/groups" + PATH_VARIABLE + "/boards/boardList", "GET"),
		HttpRequest.of("/groups" + PATH_VARIABLE + "/boards/search", "GET"),
		HttpRequest.of("/group-members" + PATH_VARIABLE, "DELETE"),
		HttpRequest.of("/group-members" + PATH_VARIABLE, "POST"),
		HttpRequest.of("/group-members" + PATH_VARIABLE, "GET"),
		HttpRequest.of("/group-members/not-in" + PATH_VARIABLE + "/search", "GET")
	);
	private static final List<HttpRequest> API_AUTHORIZATION_OF_OWNER_LIST = List.of(
		HttpRequest.of("/groups" + PATH_VARIABLE, "DELETE")
	);
	private final ObjectMapper objectMapper = new ObjectMapper();

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
		throws IOException, ServletException {
		final HttpServletRequest httpServletRequest = (HttpServletRequest)request;
		final HttpSession session = httpServletRequest.getSession(false);
		final Authentication authentication = (Authentication)session.getAttribute(AUTHENTICATION);
		final String path = httpServletRequest.getRequestURI();
		final String method = httpServletRequest.getMethod();
		if (this.isAuthorizedForMemberRequest(path, method)) {
			final Long channelId = Long.valueOf(extractPathVariable(path));
			if (authentication.getChannelAuthorityMap().containsKey(channelId)) {
				final ChannelRoles roles = authentication.getChannelAuthorityMap().get(channelId);
				if (roles.equals(ChannelRoles.MEMBER) || roles.equals(ChannelRoles.OWNER)) {
					chain.doFilter(request, response);
				} else {
					handleAuthorizationFailure((HttpServletResponse)response);
				}
			} else {
				handleAuthorizationFailure((HttpServletResponse)response);
			}
		} else if (this.isAuthorizedForOwnerRequest(path, method)) {
			final Long channelId = Long.valueOf(extractPathVariable(path));
			if (authentication.getChannelAuthorityMap().containsKey(channelId)) {
				final ChannelRoles roles = authentication.getChannelAuthorityMap().get(channelId);
				if (roles.equals(ChannelRoles.OWNER)) {
					chain.doFilter(request, response);
				} else {
					handleAuthorizationFailure((HttpServletResponse)response);
				}
			} else {
				handleAuthorizationFailure((HttpServletResponse)response);
			}
		} else {
			chain.doFilter(request, response);
		}
	}

	private void handleAuthorizationFailure(HttpServletResponse httpResponse) {
		httpResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
		httpResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
		try (OutputStream os = httpResponse.getOutputStream()) {
			objectMapper.writeValue(os, ErrorResponse.of(AUTHORIZATION_FAILURE));
			os.flush();
		} catch (Exception exception) {
			exception.printStackTrace();
			throw new RuntimeException(exception.getMessage(), exception.getCause());
		}
	}

	private String extractPathVariable(String path) {
		final Pattern pattern = Pattern.compile(PATH_VARIABLE);
		final Matcher matcher = pattern.matcher(path);
		if (matcher.find()) {
			return matcher.group().substring(1);
		}
		throw new RuntimeException();
	}

	private boolean isAuthorizedForMemberRequest(String path, String method) {
		return isAuthorizedRequest(path, method, API_AUTHORIZATION_OF_MEMBER_LIST);
	}

	private boolean isAuthorizedForOwnerRequest(String path, String method) {
		return isAuthorizedRequest(path, method, API_AUTHORIZATION_OF_OWNER_LIST);
	}

	private boolean isAuthorizedRequest(String path, String method,
		List<HttpRequest> apiAuthorizationOfMemberList) {
		for (HttpRequest httpRequest : apiAuthorizationOfMemberList) {
			final String patternString = "^" + httpRequest.getRequestUriPattern() + "$";
			final Pattern pattern = Pattern.compile(patternString);
			final Matcher matcher = pattern.matcher(path);

			if (matcher.matches() && httpRequest.getMethod().equals(method)) {
				return true;
			}
		}
		return false;
	}

	@Getter
	@AllArgsConstructor
	public static class HttpRequest {

		private String requestUriPattern;
		private String method;

		public static HttpRequest of(String requestUriPattern, String method) {
			return new HttpRequest(requestUriPattern, method);
		}

	}

}
