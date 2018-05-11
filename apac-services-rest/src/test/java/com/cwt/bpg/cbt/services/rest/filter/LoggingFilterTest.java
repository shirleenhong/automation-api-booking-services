package com.cwt.bpg.cbt.services.rest.filter;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Test;
import org.mockito.Mockito;

public class LoggingFilterTest {

	private LoggingFilter filter = new LoggingFilter();

	@Test
	public void canDoFilterChain() throws ServletException, IOException {
		FilterChain filterChain = mock(FilterChain.class);
		HttpServletResponse response = mock(HttpServletResponse.class);
		HttpServletRequest request = mock(HttpServletRequest.class);

		when(response.getCharacterEncoding()).thenReturn("UTF-8");

		filter.doFilterInternal(request, response, filterChain);

		verify(filterChain, times(1)).doFilter(Mockito.any(ServletRequest.class),
				Mockito.any(ServletResponse.class));

	}

}
