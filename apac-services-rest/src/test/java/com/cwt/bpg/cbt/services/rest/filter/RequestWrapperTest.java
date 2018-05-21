package com.cwt.bpg.cbt.services.rest.filter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;

import java.io.IOException;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;

import org.junit.Test;

public class RequestWrapperTest {

	@Test
	public void canReturnOuputStream() throws IOException {
		HttpServletRequest request = mock(HttpServletRequest.class);
		RequestWrapper wrapper = new RequestWrapper(1L, request);
		ServletInputStream inputStream = wrapper.getInputStream();
		
		assertNotNull(inputStream);
		assertEquals(1L, wrapper.getId());
		
		assertFalse(inputStream.isReady());
	}
	
	@Test
	public void canSetId() throws IOException {
		HttpServletRequest request = mock(HttpServletRequest.class);
		RequestWrapper wrapper = new RequestWrapper(1L, request);
		wrapper.setId(8L);
		assertEquals(8L, wrapper.getId());
	}
}
