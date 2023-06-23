package com.sds.actlongs.filter;

import static org.mockito.BDDMockito.*;

import java.util.List;

import javax.servlet.FilterChain;
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

import com.sds.actlongs.domain.member.entity.Member;
import com.sds.actlongs.model.Authentication;
import com.sds.actlongs.util.SessionConstants;

@ExtendWith(MockitoExtension.class)
class AuthenticationFilterTest {

	private static final String AUTHENTICATION_PATH = "/members/info";
	private static final String AUTHENTICATION_WHITELIST_PATH = "/members/login";
	@InjectMocks
	private AuthenticationFilter subject;
	@Mock
	private HttpServletRequest request;
	@Mock
	private HttpServletResponse response;
	@Mock
	private FilterChain filterChain;
	@Mock
	private HttpSession session;
	@Mock
	private ServletOutputStream outputStream;

	@Nested
	class DoFilter {

		@Test
		void ifAuthenticationPathAndSessionValidThenCallFilterChainDoFilter() throws Exception {
			// given
			given(request.getRequestURI()).willReturn(AUTHENTICATION_PATH);

			final Authentication authentication = Authentication.of(Member.createNewMember("mandoo"), List.of());
			given(request.getSession(false)).willReturn(session);
			given(session.getAttribute(SessionConstants.AUTHENTICATION)).willReturn(authentication);

			// when
			subject.doFilter(request, response, filterChain);

			// then
			then(filterChain).should(times(1)).doFilter(request, response);
		}

		@Test
		void ifAuthenticationPathAndSessionInvalidThenDoNotCallFilterChainDoFilter() throws Exception {
			// given
			given(request.getRequestURI()).willReturn(AUTHENTICATION_PATH);
			given(response.getOutputStream()).willReturn(outputStream);

			// when
			subject.doFilter(request, response, filterChain);

			// then
			then(filterChain).should(never()).doFilter(request, response);
		}

		@Test
		void ifAuthenticationWhitelistPathAndSessionValidThenCallFilterChainDoFilter() throws Exception {
			// given
			given(request.getRequestURI()).willReturn(AUTHENTICATION_WHITELIST_PATH);

			// when
			subject.doFilter(request, response, filterChain);

			// then
			then(filterChain).should(times(1)).doFilter(request, response);
		}

	}

}
