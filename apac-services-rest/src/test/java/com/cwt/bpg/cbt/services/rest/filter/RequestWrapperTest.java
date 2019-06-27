package com.cwt.bpg.cbt.services.rest.filter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;

import org.junit.Test;

public class RequestWrapperTest
{

    @Test
    public void canReturnOuputStream() throws IOException
    {
        HttpServletRequest request = mock(HttpServletRequest.class);
        final String id = UUID.randomUUID().toString();
        RequestWrapper wrapper = new RequestWrapper(id, request);
        ServletInputStream inputStream = wrapper.getInputStream();
        inputStream.setReadListener(null);

        assertNotNull(inputStream);
        assertEquals(id, wrapper.getId());

        assertFalse(inputStream.isReady());
        assertFalse(inputStream.isFinished());
    }

    @Test
    public void canSetId() throws IOException
    {
        HttpServletRequest request = mock(HttpServletRequest.class);
        final String id = UUID.randomUUID().toString();
        RequestWrapper wrapper = new RequestWrapper(UUID.randomUUID().toString(), request);
        wrapper.setId(id);
        assertEquals(id, wrapper.getId());
        assertNotNull(wrapper.toByteArray());
    }

}
