package com.cwt.bpg.cbt.services.rest.filter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.UUID;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.MDC;

public class ResponseWrapperTest
{

    ResponseWrapper wrapper;
    HttpServletResponse response;

    @Before
    public void setup()
    {
        response = mock(HttpServletResponse.class);
        wrapper = new ResponseWrapper(UUID.randomUUID().toString(), response);

    }

    @Test
    public void canReturnOuputStream() throws IOException
    {
        ServletOutputStream outputStream = wrapper.getOutputStream();

        outputStream.setWriteListener(null);
        assertNotNull(outputStream);
        assertFalse(outputStream.isReady());
    }

    @Test
    public void canReturnWriter() throws IOException
    {
        PrintWriter writer = mock(PrintWriter.class);
        when(response.getWriter()).thenReturn(writer);

        assertNotNull(wrapper.getWriter());
    }

    @Test
    public void canGetResponse() throws IOException
    {
        PrintWriter writer = mock(PrintWriter.class);
        when(response.getWriter()).thenReturn(writer);
        final String id = UUID.randomUUID().toString();
        wrapper.setId(id);
        
        assertNotNull(wrapper.getResponse());
        assertEquals(id, wrapper.getId());
    }

    @Test
    public void canGetStatusHeader() throws IOException
    {
        PrintWriter writer = mock(PrintWriter.class);
        when(response.getWriter()).thenReturn(writer);
        when(response.getHeader(ResponseWrapper.EXECUTION_TIME_KEY)).thenReturn("test");
        MDC.put(ResponseWrapper.START_TIME_KEY, String.valueOf(System.currentTimeMillis()));

        wrapper.setStatus(9);

        assertNotNull(wrapper.getHeader(ResponseWrapper.EXECUTION_TIME_KEY));

    }

}
