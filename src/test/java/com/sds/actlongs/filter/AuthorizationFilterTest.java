package com.sds.actlongs.filter;

import static com.sds.actlongs.util.SessionConstants.*;
import static org.mockito.BDDMockito.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.sds.actlongs.model.Authentication;
import com.sds.actlongs.model.Authentication.ChannelRoles;

@ExtendWith(MockitoExtension.class)
class AuthorizationFilterTest {

	private static final String PATH_VARIABLE = "/\\d+";
	private static final List<AuthorizationFilter.HttpRequest> API_AUTHORIZATION_OF_MEMBER_LIST = List.of(
		AuthorizationFilter.HttpRequest.of("/groups" + PATH_VARIABLE + "/boards" + PATH_VARIABLE, "GET"),
		AuthorizationFilter.HttpRequest.of("/groups" + PATH_VARIABLE + "/boards" + PATH_VARIABLE, "PATCH"),
		AuthorizationFilter.HttpRequest.of("/groups" + PATH_VARIABLE + "/boards" + PATH_VARIABLE, "DELETE"),
		AuthorizationFilter.HttpRequest.of("/groups" + PATH_VARIABLE + "/boards", "POST"),
		AuthorizationFilter.HttpRequest.of("/groups" + PATH_VARIABLE + "/boards", "GET"),
		AuthorizationFilter.HttpRequest.of("/groups" + PATH_VARIABLE + "/boards/search", "GET"),
		AuthorizationFilter.HttpRequest.of("/group-members" + PATH_VARIABLE, "DELETE"),
		AuthorizationFilter.HttpRequest.of("/group-members" + PATH_VARIABLE, "POST"),
		AuthorizationFilter.HttpRequest.of("/group-members" + PATH_VARIABLE, "GET"),
		AuthorizationFilter.HttpRequest.of("/group-members/not-in" + PATH_VARIABLE + "/search", "GET")
	);
	private static final List<AuthorizationFilter.HttpRequest> API_AUTHORIZATION_OF_OWNER_LIST = List.of(
		AuthorizationFilter.HttpRequest.of("/groups" + PATH_VARIABLE, "DELETE")
	);
	@InjectMocks
	private AuthorizationFilter subject;
	@Mock
	private HttpServletRequest mockRequest;
	@Mock
	private HttpServletResponse mockResponse;
	@Mock
	private FilterChain mockFilterChain;
	@Mock
	private HttpSession mockSession;
	@Mock
	private ServletOutputStream mockOutputStream;

	@Nested
	class DoFilter {

		@Test
		void ifAuthorizedForMemberRequestThenCallDoFilter() throws ServletException, IOException {
			// given
			given(mockRequest.getRequestURI()).willReturn("/groups/1/boards/1");
			given(mockRequest.getMethod()).willReturn("GET");
			given(mockRequest.getSession(false)).willReturn(mockSession);

			final Authentication mockAuthentication = mock(Authentication.class);
			final long channelId = 1L;
			final ChannelRoles channelRoles = ChannelRoles.OWNER;
			given(mockAuthentication.getChannelAuthorityMap()).willReturn(Map.of(channelId, channelRoles));
			given(mockSession.getAttribute(AUTHENTICATION)).willReturn(mockAuthentication);

			// when
			subject.doFilter(mockRequest, mockResponse, mockFilterChain);

			// then
			then(mockFilterChain).should(times(1)).doFilter(mockRequest, mockResponse);
		}

		@Test
		void ifAuthorizedForOwnerRequestThenCallDoFilter() throws ServletException, IOException {
			// given
			given(mockRequest.getRequestURI()).willReturn("/groups/1");
			given(mockRequest.getMethod()).willReturn("DELETE");
			given(mockRequest.getSession(false)).willReturn(mockSession);

			final Authentication mockAuthentication = mock(Authentication.class);
			final long channelId = 1L;
			final ChannelRoles channelRoles = ChannelRoles.OWNER;
			given(mockAuthentication.getChannelAuthorityMap()).willReturn(Map.of(channelId, channelRoles));
			given(mockSession.getAttribute(AUTHENTICATION)).willReturn(mockAuthentication);

			// when
			subject.doFilter(mockRequest, mockResponse, mockFilterChain);

			// then
			then(mockFilterChain).should(times(1)).doFilter(mockRequest, mockResponse);
		}

		@Test
		void ifAuthorizedForOwnerRequestButNotEnoughAuthorityThenDoNotCallDoFilter()
			throws ServletException, IOException {
			// given
			given(mockRequest.getRequestURI()).willReturn("/groups/1");
			given(mockRequest.getMethod()).willReturn("DELETE");
			given(mockRequest.getSession(false)).willReturn(mockSession);
			given(mockResponse.getOutputStream()).willReturn(mockOutputStream);

			final Authentication mockAuthentication = mock(Authentication.class);
			final long channelId = 1L;
			final ChannelRoles channelRoles = ChannelRoles.MEMBER;
			given(mockAuthentication.getChannelAuthorityMap()).willReturn(Map.of(channelId, channelRoles));
			given(mockSession.getAttribute(AUTHENTICATION)).willReturn(mockAuthentication);

			// when
			subject.doFilter(mockRequest, mockResponse, mockFilterChain);

			// then
			then(mockFilterChain).should(never()).doFilter(mockRequest, mockResponse);
		}

		@Test
		void ifAuthorizedForOwnerRequestButNoAuthorityThenDoNotCallDoFilter() throws ServletException, IOException {
			// given
			given(mockRequest.getRequestURI()).willReturn("/groups/1/boards/1");
			given(mockRequest.getMethod()).willReturn("GET");
			given(mockRequest.getSession(false)).willReturn(mockSession);
			given(mockResponse.getOutputStream()).willReturn(mockOutputStream);

			final Authentication mockAuthentication = mock(Authentication.class);
			given(mockAuthentication.getChannelAuthorityMap()).willReturn(Map.of());
			given(mockSession.getAttribute(AUTHENTICATION)).willReturn(mockAuthentication);

			// when
			subject.doFilter(mockRequest, mockResponse, mockFilterChain);

			// then
			then(mockFilterChain).should(never()).doFilter(mockRequest, mockResponse);
		}

		@Test
		void ifUnnecessaryAuthorizedRequestThenCallDoFilter() throws ServletException, IOException {
			// given
			given(mockRequest.getRequestURI()).willReturn("/members");
			given(mockRequest.getMethod()).willReturn("GET");
			given(mockRequest.getSession(false)).willReturn(mockSession);
			given(mockSession.getAttribute(AUTHENTICATION)).willReturn(mock(Authentication.class));

			// when
			subject.doFilter(mockRequest, mockResponse, mockFilterChain);

			// then
			then(mockFilterChain).should(times(1)).doFilter(mockRequest, mockResponse);
		}

	}

}
