package com.cwt.bpg.cbt.services.rest.filter;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.UUID;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.http.MediaType;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class LoggingFilter extends OncePerRequestFilter
{

    private static final String NOTIFICATION_PREFIX = "* ";

    private static final String REQUEST_PREFIX = "> ";
    private static final String RESPONSE_PREFIX = "< ";

    private static final String UUID_KEY = "UUID";

    private static final Set<String> READABLE_APP_MEDIA_TYPES = new HashSet<>();

    static
    {
        READABLE_APP_MEDIA_TYPES.add("text");
        READABLE_APP_MEDIA_TYPES.add(MediaType.APPLICATION_ATOM_XML.toString());
        READABLE_APP_MEDIA_TYPES.add(MediaType.APPLICATION_FORM_URLENCODED.toString());
        READABLE_APP_MEDIA_TYPES.add(MediaType.APPLICATION_JSON.toString());
        READABLE_APP_MEDIA_TYPES.add(MediaType.APPLICATION_XHTML_XML.toString());
        READABLE_APP_MEDIA_TYPES.add(MediaType.APPLICATION_XML.toString());
    }

    protected static final Logger LOG = LoggerFactory.getLogger(LoggingFilter.class);

    private static final Logger TRANSACTION_LOG = LoggerFactory.getLogger("transaction.logger");

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
            final FilterChain filterChain) throws ServletException, IOException
    {
        long startTime = System.currentTimeMillis();
        MDC.put(ResponseWrapper.START_TIME_KEY, String.valueOf(startTime));

        String uuid = resolveTransactionId(request);
        MDC.put(UUID_KEY, uuid);

        RequestWrapper wrappedRequest = new RequestWrapper(uuid, request);
        ResponseWrapper wrappedResponse = new ResponseWrapper(uuid, response);

        try
        {
            response.addHeader(UUID_KEY, uuid);
            filterChain.doFilter(wrappedRequest, wrappedResponse);
        }
        finally
        {
            // TeeInputStream creates a copy to after the stream is read
            logRequest(wrappedRequest, uuid);
            logResponse((ResponseWrapper) wrappedResponse, response);
            logTransaction(request, response, startTime, uuid);
        }
    }

    private void logTransaction(final HttpServletRequest request, final HttpServletResponse response, long startTime, String uuid) throws JsonProcessingException
    {
        final long endTime = System.currentTimeMillis();
        final Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(endTime);

        final DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
        final String endTimeStr = formatter.format(calendar.getTime());

        final TransactionLog transactionLog = new TransactionLog(uuid, request.getMethod(), request.getRequestURI(),
                response.getStatus(), endTime - startTime, endTimeStr);

        final Enumeration<String> parameterNames = request.getParameterNames();
        if (parameterNames != null)
        {
            while (parameterNames.hasMoreElements())
            {
                final String key = parameterNames.nextElement();
                final String value = request.getParameter(key);
                transactionLog.addParameter(key, value);
            }
        }

        final Enumeration<String> headerNames = request.getHeaderNames();
        if (headerNames != null)
        {
            while (headerNames.hasMoreElements())
            {
                final String key = headerNames.nextElement();
                final String value = request.getHeader(key);
                transactionLog.addHeader(key, value);
            }
        }

        final ObjectMapper obj = new ObjectMapper();
        final String jsonStr = obj.writeValueAsString(transactionLog);
        TRANSACTION_LOG.info(jsonStr);
    }

    private String resolveTransactionId(HttpServletRequest request)
    {
        final String transactionId = request.getHeader("transactionId");
        return StringUtils.isNotBlank(transactionId) ? transactionId : UUID.randomUUID().toString();
    }

    private void logRequest(final HttpServletRequest request, String requestId)
    {

        final StringBuilder b = new StringBuilder();

        printRequestLine(b, "Server has received a request",
                requestId,
                request.getMethod(),
                request.getQueryString() != null
                        ? request.getRequestURI().concat("?").concat(request.getQueryString())
                        : request.getRequestURI());

        printPrefixedHeaders(b, requestId, REQUEST_PREFIX, request);

        if (request instanceof RequestWrapper && isReadable(request.getContentType()))
        {

            RequestWrapper requestWrapper = (RequestWrapper) request;
            try
            {
                String charEncoding = requestWrapper.getCharacterEncoding() != null
                        ? requestWrapper.getCharacterEncoding()
                        : "UTF-8";

                prefixId(b, requestId).append(REQUEST_PREFIX)
                        .append(new String(requestWrapper.toByteArray(), charEncoding));
            }
            catch (IOException e)
            {
                LOG.warn("Failed to parse request payload", e);
            }
        }
        log(b);
    }

    private void log(final StringBuilder b)
    {
        String s = b.toString();
        if (!s.isEmpty())
        {
            LOG.info(s);
        }
    }

    private void logResponse(final ResponseWrapper response, HttpServletResponse httpResponse)
    {

        final String requestId = response.getId();
        final StringBuilder b = new StringBuilder();

        printResponseLine(b, "Server responded with a response", requestId, httpResponse.getStatus());
        printPrefixedHeaders(b, requestId, RESPONSE_PREFIX, httpResponse);

        try
        {
            if (isReadable(response.getContentType()))
            {
                prefixId(b, requestId).append(RESPONSE_PREFIX)
                        .append(new String(response.toByteArray(), response.getCharacterEncoding()));
            }
        }
        catch (UnsupportedEncodingException e)
        {
            LOG.warn("Failed to parse response payload", e);
        }

        log(b);
    }

    private void printPrefixedHeaders(StringBuilder b, String id, String prefix, HttpServletResponse response)
    {

        Iterator<String> headers = response.getHeaderNames().iterator();
        while (headers.hasNext())
        {
            String key = headers.next();
            prefixId(b, id).append(prefix).append(key).append(": ").append(response.getHeader(key)).append("\n");
        }
    }

    private void printRequestLine(final StringBuilder b, final String note, final String id, final String method,
            final String uri)
    {

        prefixId(b, id).append(NOTIFICATION_PREFIX)
                .append(note)
                .append(" on thread ")
                .append(Thread.currentThread().getName())
                .append("\n");

        prefixId(b, id).append(REQUEST_PREFIX).append(method).append(" ").append(uri).append("\n");
    }

    private void printResponseLine(final StringBuilder b, final String note, final String id, final int status)
    {
        prefixId(b, id).append(NOTIFICATION_PREFIX)
                .append(note)
                .append(" on thread ")
                .append(Thread.currentThread().getName())
                .append("\n");
        prefixId(b, id).append(RESPONSE_PREFIX).append(Integer.toString(status)).append("\n");
    }

    private StringBuilder prefixId(final StringBuilder b, final String id)
    {
        b.append(id).append(" ");
        return b;
    }

    private void printPrefixedHeaders(final StringBuilder b, final String id, final String prefix,
            final HttpServletRequest request)
    {

        Enumeration<String> headers = request.getHeaderNames();

        while (headers.hasMoreElements())
        {
            String key = headers.nextElement();
            prefixId(b, id).append(prefix).append(key).append(": ").append(request.getHeader(key)).append("\n");
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
