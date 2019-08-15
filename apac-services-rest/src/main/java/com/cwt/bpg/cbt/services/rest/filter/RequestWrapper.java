package com.cwt.bpg.cbt.services.rest.filter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.apache.commons.io.input.TeeInputStream;

public class RequestWrapper extends HttpServletRequestWrapper
{

    private final ByteArrayOutputStream bos = new ByteArrayOutputStream();
    private String id;

    public RequestWrapper(String id, HttpServletRequest request)
    {
        super(request);
        this.id = id;
    }

    @Override
    public ServletInputStream getInputStream() throws IOException
    {

        return new ServletInputStream()
        {

            private TeeInputStream tee = new TeeInputStream(
                    RequestWrapper.super.getInputStream(), bos);

            @Override
            public boolean isFinished()
            {
                return false;
            }

            @Override
            public boolean isReady()
            {
                return false;
            }

            @Override
            public void setReadListener(ReadListener readListener)
            {
                // Do nothing
            }

            @Override
            public int read() throws IOException {
                return tee.read();
            }

            @Override
            public void close() throws IOException {
                tee.close();
            }

            @Override
            public int read(byte[] b) throws IOException {
                return tee.read(b);
            }

            @Override
            public int read(byte[] b, int off, int len) throws IOException {
                return tee.read(b, off, len);
            }
        };
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