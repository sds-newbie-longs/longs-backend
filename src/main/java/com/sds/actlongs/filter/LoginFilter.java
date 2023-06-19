package com.sds.actlongs.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginFilter implements Filter {

	private static final String API_LOGIN = "/members/login";

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
		throws ServletException, IOException {
		HttpServletRequest request = (HttpServletRequest)req;
		HttpServletResponse response = (HttpServletResponse)res;

		if (API_LOGIN.equals(request.getRequestURI())) {
			chain.doFilter(request, new CustomHttpServletResponseWrapper(response));
		} else {
			chain.doFilter(request, response);
		}
	}

}
