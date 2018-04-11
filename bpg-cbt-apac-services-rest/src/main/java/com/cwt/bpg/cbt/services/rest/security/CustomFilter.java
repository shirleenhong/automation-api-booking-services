package com.cwt.bpg.cbt.services.rest.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import com.cwt.bpg.cbt.security.api.TokenApi;

public class CustomFilter extends GenericFilterBean {

	private TokenApi tokenApi;

	public CustomFilter(TokenApi tokenApi) {
		this.tokenApi = tokenApi;
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;

		String authHeader = httpRequest.getHeader("Authorization");
		if (StringUtils.isBlank(authHeader) || !authHeader.startsWith("Bearer ")) {
			httpResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, "Authorization header needed");
			return;
		}

		String token = authHeader.substring(7).trim();
		if (!tokenApi.isTokenExist(token)) {
			httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid token.");
			return;
		}
		chain.doFilter(request, response);
	}

}
