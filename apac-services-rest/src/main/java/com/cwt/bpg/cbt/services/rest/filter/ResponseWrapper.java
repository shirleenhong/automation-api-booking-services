package com.cwt.bpg.cbt.services.rest.filter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletOutputStream;
import javax.servlet.ServletResponse;
import javax.servlet.WriteListener;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import org.apache.commons.io.output.TeeOutputStream;
import org.slf4j.MDC;

public class ResponseWrapper extends HttpServletResponseWrapper
{

    private final ByteArrayOutputStream bos = new ByteArrayOutputStream();
    private PrintWriter writer = new PrintWriter(bos);
    private String id;

    protected static final String START_TIME_KEY = "Start-Time";
    protected static final String EXECUTION_TIME_KEY = "Execution-Time-In-Milliseconds";

    public ResponseWrapper(String id, HttpServletResponse response)
    {
        super(response);
        this.id = id;
    }

    @Override
    public ServletResponse getResponse()
    {
        return this;
    }

    @Override
    public void setStatus(int sc)
    {
        super.setStatus(sc);

        String startTime = MDC.get(START_TIME_KEY);

        long executionTime = System.currentTimeMillis() - Long.parseLong(startTime);
        addHeader(EXECUTION_TIME_KEY, String.valueOf(executionTime));
    }

    @Override
    public ServletOutputStream getOutputStream() throws IOException
    {
        return new ServletOutputStream()
        {

            private TeeOutputStream tee = new TeeOutputStream(ResponseWrapper.super.getOutputStream(), bos);

            @Override
            public boolean isReady()
            {
                return false;
            }

            @Override
            public void setWriteListener(WriteListener writeListener)
            {
                // Do nothing
            }

            @Override
            public void write(int b) throws IOException
            {
                tee.write(b);
            }

            @Override
            public void close() throws IOException {
                tee.close();
            }

            @Override
            public void write(byte[] b) throws IOException {
                tee.write(b);
            }

            @Override
            public void write(byte[] b, int off, int len) throws IOException {
                tee.write(b, off, len);
            }
        };
    }

    @Override
    public PrintWriter getWriter() throws IOException
    {
        return new TeePrintWriter(super.getWriter(), writer);
    }

    public byte[] toByteArray()
    {
        return bos.toByteArray();
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

}
