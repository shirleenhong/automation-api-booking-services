package com.cwt.bpg.cbt.services.rest.filter;

import org.junit.Before;
import org.junit.Test;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ResponseWrapperTest {

    ResponseWrapper wrapper;
    HttpServletResponse response;

    @Before
    public void setup(){
        response = mock(HttpServletResponse.class);
        wrapper = new ResponseWrapper(1L, response);

    }

	@Test
	public void canReturnOuputStream() throws IOException {
        ServletOutputStream outputStream = wrapper.getOutputStream();
		
		assertNotNull(outputStream);
		assertFalse(outputStream.isReady());
	}

	@Test
	public void canReturnWriter() throws IOException {
		PrintWriter writer = mock(PrintWriter.class);
		when(response.getWriter()).thenReturn(writer);

		assertNotNull(wrapper.getWriter());
	}
}
