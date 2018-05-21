package com.cwt.bpg.cbt.services.rest.filter;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.http.MediaType;
import org.springframework.web.filter.OncePerRequestFilter;

public class LoggingFilter extends OncePerRequestFilter {

	private static final String NOTIFICATION_PREFIX = "* ";

    private static final String REQUEST_PREFIX = "> ";
    private static final String RESPONSE_PREFIX = "< ";
    
    private static final String UUID_KEY = "UUID";

	private static final AtomicLong id = new AtomicLong(0);	

    private static final Set<String> READABLE_APP_MEDIA_TYPES = new HashSet<String>()
    {
        private static final long serialVersionUID = 1L;
        {
            add("text");
            add(MediaType.APPLICATION_ATOM_XML.toString());
            add(MediaType.APPLICATION_FORM_URLENCODED.toString());
            add(MediaType.APPLICATION_JSON.toString());
            add(MediaType.APPLICATION_XHTML_XML.toString());
            add(MediaType.APPLICATION_XML.toString());
        }
    };
	
	protected static final Logger LOGGER = LoggerFactory.getLogger(LoggingFilter.class);

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
			final FilterChain filterChain) throws ServletException, IOException {

		MDC.put(ResponseWrapper.START_TIME_KEY, String.valueOf(System.currentTimeMillis()));
		final long requestId = id.incrementAndGet();
		
		RequestWrapper wrappedRequest = new RequestWrapper(requestId, request);
		ResponseWrapper wrappedResponse = new ResponseWrapper(requestId, response);

		try {
			response.addHeader(UUID_KEY, UUID.randomUUID().toString());
			filterChain.doFilter(wrappedRequest, wrappedResponse);
		}
		finally {
			logRequest(wrappedRequest, requestId);
			logResponse((ResponseWrapper) wrappedResponse, response);
		}

	}

	private void logRequest(final HttpServletRequest request, long requestId) {

		final StringBuilder b = new StringBuilder();
		
		printRequestLine(b,
				"Server has received a request",
				requestId,
				request.getMethod(),
				request.getRequestURI());

		printPrefixedHeaders(b, requestId, REQUEST_PREFIX, request);
		
		if (request instanceof RequestWrapper && isReadable(request.getContentType())) {

			RequestWrapper requestWrapper = (RequestWrapper) request;
			try {
				String charEncoding = requestWrapper.getCharacterEncoding() != null ? requestWrapper
						.getCharacterEncoding() : "UTF-8";
					
				prefixId(b, requestId)
						.append(REQUEST_PREFIX)
						.append(new String(requestWrapper.toByteArray(), charEncoding));
			}
			catch (IOException e) {
				LOGGER.warn("Failed to parse request payload", e);
			}
			
		}
		
		log(b.toString());
	}
	
	private void log(String message) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug(message);
		}
	}

	private void logResponse(final ResponseWrapper response, HttpServletResponse httpResponse) {
		
		final long requestId = response.getId();
		final StringBuilder b = new StringBuilder();

        printResponseLine(b, "Server responded with a response", requestId, httpResponse.getStatus());
        printPrefixedHeaders(b, requestId, RESPONSE_PREFIX, httpResponse);

		try {
			if (isReadable(response.getContentType())) {
				prefixId(b, requestId)
						.append(RESPONSE_PREFIX)
						.append(new String(response.toByteArray(), response.getCharacterEncoding()));
			}			
		}
		catch (UnsupportedEncodingException e) {
			LOGGER.warn("Failed to parse response payload", e);
		}
		
		log(b.toString());
	}

	private void printPrefixedHeaders(StringBuilder b, long id, String prefix,
			HttpServletResponse response) {
		
		Iterator<String> headers = response.getHeaderNames().iterator();

		while (headers.hasNext()) {

			String key = headers.next();
			
			prefixId(b, id)
				.append(prefix)
				.append(key)
				.append(": ")
				.append(response.getHeader(key))
				.append("\n");
		}
		
	}

	private void printRequestLine(final StringBuilder b, final String note, final long id,
			final String method, final String uri) {
		
		prefixId(b, id).append(NOTIFICATION_PREFIX).append(note).append(" on thread ")
				.append(Thread.currentThread().getName()).append("\n");
		
		prefixId(b, id).append(REQUEST_PREFIX).append(method).append(" ").append(uri).append("\n");
	}

	private void printResponseLine(final StringBuilder b, final String note, final long id,
			final int status) {
		prefixId(b, id).append(NOTIFICATION_PREFIX).append(note).append(" on thread ")
				.append(Thread.currentThread().getName()).append("\n");
		prefixId(b, id).append(RESPONSE_PREFIX).append(Integer.toString(status)).append("\n");
	}

	private StringBuilder prefixId(final StringBuilder b, final long id) {
		b.append(Long.toString(id)).append(" ");
		return b;
	}

	private void printPrefixedHeaders(final StringBuilder b, final long id, final String prefix,
			final HttpServletRequest request) {

		Enumeration<String> headers = request.getHeaderNames();

		while (headers.hasMoreElements()) {

			String key = headers.nextElement();
			
			prefixId(b, id)
				.append(prefix)
				.append(key)
				.append(": ")
				.append(request.getHeader(key))
				.append("\n");
		}
	}
	
	static boolean isReadable(String type)
    {
        if (type != null)
        {
            for (String readableMediaType : READABLE_APP_MEDIA_TYPES)
            {
                if (type.contains(readableMediaType))
                {
                    return true;
                }
            }
        }
        return false;
    }

}