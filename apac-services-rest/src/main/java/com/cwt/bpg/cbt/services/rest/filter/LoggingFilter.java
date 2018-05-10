package com.cwt.bpg.cbt.services.rest.filter;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.atomic.AtomicLong;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * Reference: https://github.com/isrsal/spring-mvc-logger/
 *
 */
public class LoggingFilter extends OncePerRequestFilter {

	protected static final Logger LOGGER = LoggerFactory.getLogger(LoggingFilter.class);
	private static final String REQUEST_PREFIX = "Request: ";
	private static final String RESPONSE_PREFIX = "Response: ";
	private AtomicLong id = new AtomicLong(1);

	@Override
	protected void doFilterInternal(HttpServletRequest request,
			HttpServletResponse response, final FilterChain filterChain)
			throws ServletException, IOException {

		long requestId = id.incrementAndGet();
		RequestWrapper wrappedRequest = new RequestWrapper(requestId, request);
		ResponseWrapper wrappedResponse = new ResponseWrapper(requestId, response);

		try {
			filterChain.doFilter(wrappedRequest, wrappedResponse);
		}
		finally {
			logRequest(wrappedRequest);
			logResponse((ResponseWrapper) wrappedResponse);
		}

	}

	private void logRequest(final HttpServletRequest request) {
		StringBuilder msg = new StringBuilder();
		msg.append(REQUEST_PREFIX);
		if (request instanceof RequestWrapper) {
			msg.append("request id=").append(((RequestWrapper) request).getId())
					.append("; ");
		}
		if (request.getMethod() != null) {
			msg.append("method=").append(request.getMethod()).append("; ");
		}
		if (request.getContentType() != null) {
			msg.append("content type=").append(request.getContentType()).append("; ");
		}
		msg.append("uri=").append(request.getRequestURI());
		if (request.getQueryString() != null) {
			msg.append('?').append(request.getQueryString());
		}

		if (request instanceof RequestWrapper) {
			RequestWrapper requestWrapper = (RequestWrapper) request;
			try {
				String charEncoding = requestWrapper.getCharacterEncoding() != null
						? requestWrapper.getCharacterEncoding() : "UTF-8";
				msg.append("; payload=")
						.append(new String(requestWrapper.toByteArray(), charEncoding));
			}
			catch (UnsupportedEncodingException e) {
				LOGGER.warn("Failed to parse request payload", e);
			}

		}
		log(msg.toString());

	}

	private void log(String message) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug(message);
		}
	}

	private void logResponse(final ResponseWrapper response) {
		StringBuilder msg = new StringBuilder();
		msg.append(RESPONSE_PREFIX);
		msg.append("request id=").append(response.getId());
		try {
			msg.append("; payload=").append(
					new String(response.toByteArray(), response.getCharacterEncoding()));
		}
		catch (UnsupportedEncodingException e) {
			LOGGER.warn("Failed to parse response payload", e);
		}
		log(msg.toString());
	}

}