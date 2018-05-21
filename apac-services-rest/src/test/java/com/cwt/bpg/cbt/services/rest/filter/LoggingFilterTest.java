package com.cwt.bpg.cbt.services.rest.filter;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Test;
import org.mockito.Mockito;

import com.google.common.collect.Iterators;

public class LoggingFilterTest {

	private LoggingFilter filter = new LoggingFilter();
	
	@Test
	public void canDoFilterChain() throws ServletException, IOException {
		FilterChain filterChain = mock(FilterChain.class);
		HttpServletResponse response = mock(HttpServletResponse.class);
		RequestWrapper request = mock(RequestWrapper.class);

		ArrayList<String> headerNames = new ArrayList<>();
		headerNames.add("Authorization");
		
		when(request.getHeaderNames()).thenReturn(Iterators.asEnumeration(headerNames.iterator()));
		when(response.getCharacterEncoding()).thenReturn("UTF-8");
		when(response.getContentType()).thenReturn("application/json");
		when(response.getHeaderNames()).thenReturn(headerNames);

		filter.doFilterInternal(request, response, filterChain);

		verify(filterChain, times(1)).doFilter(Mockito.any(ServletRequest.class),
				Mockito.any(ServletResponse.class));

	}
	
	@Test
	public void canDoFilterChainNonReadable() throws ServletException, IOException {
		FilterChain filterChain = mock(FilterChain.class);
		HttpServletResponse response = mock(HttpServletResponse.class);
		RequestWrapper request = mock(RequestWrapper.class);

		when(request.getHeaderNames()).thenReturn((Iterators.asEnumeration(new ArrayList<String>().iterator()))); 
		when(response.getCharacterEncoding()).thenReturn("UTF-8");
		when(response.getContentType()).thenReturn("xxx");

		filter.doFilterInternal(request, response, filterChain);

		verify(filterChain, times(1)).doFilter(Mockito.any(ServletRequest.class),
				Mockito.any(ServletResponse.class));

	}

}
