package com.cwt.bpg.cbt.services.rest.filter;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

import java.io.IOException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.junit.Test;

public class ResponseWrapperTest {

	@Test
	public void canReturnOuputStream() throws IOException {
		HttpServletResponse response = mock(HttpServletResponse.class);
		ResponseWrapper wrapper = new ResponseWrapper(1L, response);
		ServletOutputStream outputStream = wrapper.getOutputStream();
		
		assertNotNull(outputStream);
		
		assertEquals(1L, wrapper.getId());
		assertNotNull(wrapper.getWriter());
		assertFalse(outputStream.isReady());
	}
}
