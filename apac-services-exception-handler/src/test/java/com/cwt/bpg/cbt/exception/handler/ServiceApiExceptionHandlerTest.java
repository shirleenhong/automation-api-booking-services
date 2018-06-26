package com.cwt.bpg.cbt.exception.handler;

import static org.junit.Assert.assertEquals;
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
		
		String errorMessage = "Invalid Input";
		HttpMessageNotReadableException ex = new HttpMessageNotReadableException(
				errorMessage);

		HttpHeaders headers = Mockito.mock(HttpHeaders.class);
		HttpStatus status = HttpStatus.BAD_REQUEST;
		WebRequest request = Mockito.mock(WebRequest.class);

		ResponseEntity<Object> handleHttpMessageNotReadable = exceptionHandler
				.handleHttpMessageNotReadable(ex, headers, status, request);

		assertNotNull(handleHttpMessageNotReadable);
		ApiError error = (ApiError) handleHttpMessageNotReadable.getBody(); 
		assertTrue(error instanceof ApiError);
		assertEquals(errorMessage, error.getMessage());
		assertEquals(HttpStatus.BAD_REQUEST, error.getStatus());
		assertTrue(error.getErrors().size() == 1);
	}

	@Test
	public void canHandleArgumentMisMatch() {

		WebRequest request = Mockito.mock(WebRequest.class);
		Object value = new String("test");
		MethodParameter param = new MethodParameter(DummyType.class.getMethods()[0], 1);
		Throwable cause = new Throwable("Invalid argument");
		MethodArgumentTypeMismatchException ex = new MethodArgumentTypeMismatchException(value,
				DummyType.class, "myrequired", param, cause);

		ResponseEntity<Object> handleHttpMessageNotReadable = exceptionHandler
				.handleMethodArgumentTypeMismatch(ex, request);

		assertNotNull(handleHttpMessageNotReadable);
		assertTrue(handleHttpMessageNotReadable.getBody() instanceof ApiError);
	}

	@Test
	public void canHandleIllegalArgument() {
		WebRequest request = Mockito.mock(WebRequest.class);

		IllegalArgumentException ex = new IllegalArgumentException("Illegal argument");

		ResponseEntity<Object> handleHttpMessageNotReadable = exceptionHandler.handleIllegalArgument(ex,
				request);

		assertNotNull(handleHttpMessageNotReadable);
		assertTrue(handleHttpMessageNotReadable.getBody() instanceof ApiError);
	}

	static class DummyType {
		public void testMethod() {
		}
	}
}
