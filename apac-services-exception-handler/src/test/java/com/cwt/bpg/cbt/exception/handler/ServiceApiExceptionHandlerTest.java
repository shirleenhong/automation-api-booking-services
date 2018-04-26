package com.cwt.bpg.cbt.exception.handler;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

public class ServiceApiExceptionHandlerTest {
	
	@InjectMocks
	private ServiceApiExceptionHandler exceptionHandler = new ServiceApiExceptionHandler();
	
	
	@Test
	public void canHandleNotReadableMessage() {
		HttpMessageNotReadableException ex = new HttpMessageNotReadableException("json body not readable, can't parse json value");
		
		HttpHeaders headers = Mockito.mock(HttpHeaders.class);
		HttpStatus status = HttpStatus.BAD_REQUEST;
		WebRequest request = Mockito.mock(WebRequest.class);
		
		ResponseEntity<Object> handleHttpMessageNotReadable = exceptionHandler.handleHttpMessageNotReadable(ex, headers, status, request);
		
		assertNotNull(handleHttpMessageNotReadable);
		assertTrue(handleHttpMessageNotReadable.getBody() instanceof ApiError);
	}
	
	
	@Test
	public void canHandleArgumentMisMatch() {

		
		WebRequest request = Mockito.mock(WebRequest.class);
		Object value = new String("test");
		MethodParameter param = new MethodParameter(DummyType.class.getMethods()[0] , 1);
		Throwable cause = new Throwable("Invalid argument")
				;
		MethodArgumentTypeMismatchException ex = new MethodArgumentTypeMismatchException(value, DummyType.class, "myrequired", param, cause );
		
		ResponseEntity<Object> handleHttpMessageNotReadable = exceptionHandler.handleMethodArgumentTypeMismatch(ex, request);
		
		assertNotNull(handleHttpMessageNotReadable);
		assertTrue(handleHttpMessageNotReadable.getBody() instanceof ApiError);
	}
	
	static class DummyType {
		public void testMethod() {
		}
	}
}