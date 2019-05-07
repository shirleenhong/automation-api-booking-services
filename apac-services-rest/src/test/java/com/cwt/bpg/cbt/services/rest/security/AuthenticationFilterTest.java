package com.cwt.bpg.cbt.services.rest.security;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.junit.Test;

import com.cwt.bpg.cbt.security.service.TokenService;

public class AuthenticationFilterTest {

	private TokenService tokenApi;
	private AuthenticationFilter authFilter;

	@Before
	public void setUp() throws Exception {
		tokenApi = mock(TokenService.class);
		authFilter = new AuthenticationFilter(tokenApi);

	}

	@Test
	public void shouldResponseBadRequestErrorForBlankAuthorizationHeader() throws IOException, ServletException {
		HttpServletRequest request = mock(HttpServletRequest.class);
		HttpServletResponse response = mock(HttpServletResponse.class);
		FilterChain chain = mock(FilterChain.class);
		authFilter.doFilter(request, response, chain);

		verify(response, times(1)).sendError(eq(HttpServletResponse.SC_BAD_REQUEST), anyString());
		verify(chain, never()).doFilter(any(HttpServletRequest.class), any(HttpServletResponse.class));
	}

	@Test
	public void shouldResponseBadRequestErrorForInvalidAuthorizationHeader() throws IOException, ServletException {
		MockedHttpRequest request = new MockedHttpRequest(mock(HttpServletRequest.class));
		request.addHeader("Authorization", "Invalid 1234567890");
		HttpServletResponse response = mock(HttpServletResponse.class);
		FilterChain chain = mock(FilterChain.class);
		authFilter.doFilter(request, response, chain);

		verify(response, times(1)).sendError(eq(HttpServletResponse.SC_BAD_REQUEST), anyString());
		verify(chain, never()).doFilter(any(HttpServletRequest.class), any(HttpServletResponse.class));
	}

	@Test
	public void shouldResponseAuthenticationError() throws IOException, ServletException {
		MockedHttpRequest request = new MockedHttpRequest(mock(HttpServletRequest.class));
		request.addHeader("Authorization", "Bearer 1234567890");
		HttpServletResponse response = mock(HttpServletResponse.class);
		FilterChain chain = mock(FilterChain.class);
		authFilter.doFilter(request, response, chain);
		when(tokenApi.isTokenExist(anyString())).thenReturn(false);

		verify(response, times(1)).sendError(eq(HttpServletResponse.SC_UNAUTHORIZED), anyString());
		verify(chain, never()).doFilter(any(HttpServletRequest.class), any(HttpServletResponse.class));
	}

	@Test
	public void shouldContinueChainFilter() throws IOException, ServletException {
		MockedHttpRequest request = new MockedHttpRequest(mock(HttpServletRequest.class));
		request.addHeader("Authorization", "Bearer 1234567890");
		MockedTokenImpl tokenApi = new MockedTokenImpl();
		tokenApi.setTokenExists(true);
		AuthenticationFilter customFilter = new AuthenticationFilter(tokenApi);
		HttpServletResponse response = mock(HttpServletResponse.class);
		FilterChain chain = mock(FilterChain.class);
		customFilter.doFilter(request, response, chain);

		verify(response, never()).sendError(eq(HttpServletResponse.SC_BAD_REQUEST), anyString());
		verify(response, never()).sendError(eq(HttpServletResponse.SC_UNAUTHORIZED), anyString());
		verify(chain, times(1)).doFilter(any(HttpServletRequest.class), any(HttpServletResponse.class));
	}
	
	@Test
	public void shouldCatchRunTimeException() throws RuntimeException, IOException, ServletException {
		MockedHttpRequest request = new MockedHttpRequest(mock(HttpServletRequest.class));
		request.addHeader("Authorization", "Bearer 1234567890");
		MockedTokenImpl tokenApi = new MockedTokenImpl();
		tokenApi.setTokenExists(true);
		AuthenticationFilter customFilter = new AuthenticationFilter(null);
		HttpServletResponse response = mock(HttpServletResponse.class);
		FilterChain chain = mock(FilterChain.class);
		customFilter.doFilter(request, response, chain);

		verify(response, times(1)).setHeader(eq("UUID"), anyString());
		verify(response, times(1)).sendError(eq(HttpServletResponse.SC_INTERNAL_SERVER_ERROR), anyString());
		verify(chain, never()).doFilter(any(HttpServletRequest.class), any(HttpServletResponse.class));
	}
}

class MockedTokenImpl extends TokenService {
	private boolean tokenExists;

	public boolean isTokenExists() {
		return tokenExists;
	}

	@Override
	public boolean isTokenExist(String tokenKey) {
		return this.isTokenExists();
	}

	public void setTokenExists(boolean tokenExists) {
		this.tokenExists = tokenExists;
	}
}

class MockedHttpRequest extends HttpServletRequestWrapper {
	private Map<String, String> headerMap = new HashMap<>();

	public MockedHttpRequest(HttpServletRequest request) {
		super(request);
	}

	@Override
	public String getHeader(String name) {
		return this.headerMap.get(name);
	}

	public void addHeader(String name, String value) {
		this.headerMap.put(name, value);
	}
}
