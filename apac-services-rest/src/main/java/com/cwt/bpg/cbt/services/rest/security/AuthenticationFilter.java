package com.cwt.bpg.cbt.services.rest.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.GenericFilterBean;

import com.cwt.bpg.cbt.security.service.TokenService;

public class AuthenticationFilter extends GenericFilterBean
{

    private static final String BEARER = "Bearer ";
    private TokenService tokenService;

    private static final Logger LOG = LoggerFactory.getLogger(AuthenticationFilter.class);

    public AuthenticationFilter(TokenService tokenService)
    {
        this.tokenService = tokenService;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException
    {
        final HttpServletRequest httpRequest = (HttpServletRequest) request;
        final HttpServletResponse httpResponse = (HttpServletResponse) response;

        String authHeader = httpRequest.getHeader("Authorization");

        if (StringUtils.isBlank(authHeader) || !authHeader.startsWith(BEARER))
        {
            httpResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, "Authorization header needed");
            return;
        }

        try
        {
            String token = authHeader.replace(BEARER, "").trim();
            if (!tokenService.isTokenExist(token))
            {
                httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid token.");
                return;
            }
            chain.doFilter(request, response);

        }
        catch (RuntimeException e)
        {
            LOG.error("Server caught an exception: {}", e.getLocalizedMessage());
            httpResponse.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Internal Server Error");
            return;
        }

    }

}
