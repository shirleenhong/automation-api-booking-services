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

public class CustomFilter extends GenericFilterBean {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;

		String authHeader = httpRequest.getHeader("Authorization");
        if (StringUtils.isBlank(authHeader) || !authHeader.startsWith("Bearer ")) {
        	httpResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, "Authorization header needed");
            return ;
        }

        String token = authHeader.substring(7);
        try {
        	System.out.println("token: "+ token);
        } catch (Exception e) {
        	httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid token.");
            return ;
        }
		chain.doFilter(request, response);
	}

}
