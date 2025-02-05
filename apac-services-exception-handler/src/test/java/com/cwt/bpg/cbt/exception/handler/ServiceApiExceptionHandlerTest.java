package com.cwt.bpg.cbt.exception.handler;

import static org.junit.Assert.*;

import java.io.IOException;

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

import com.cwt.bpg.cbt.exceptions.ApiServiceException;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;

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
	}

	@Test
	public void canHandleInvalidJson() throws IOException {

		String invalidJson = "{ \"fieldName\" : }";
		String jsonErrorMessage = "Invalid JSON";
		JsonParser parser  = new JsonFactory().createParser(invalidJson);
		JsonParseException jsonEx = new JsonParseException(parser,
				jsonErrorMessage);

		String errorMessage = "Invalid Input";
		HttpMessageNotReadableException ex = new HttpMessageNotReadableException(
				errorMessage, jsonEx);

		HttpHeaders headers = Mockito.mock(HttpHeaders.class);
		HttpStatus status = HttpStatus.BAD_REQUEST;
		WebRequest request = Mockito.mock(WebRequest.class);

		ResponseEntity<Object> handleHttpMessageNotReadable = exceptionHandler
				.handleHttpMessageNotReadable(ex, headers, status, request);

		assertNotNull(handleHttpMessageNotReadable);
		assertTrue(handleHttpMessageNotReadable.getBody() instanceof ApiError);
	}

	@Test
	public void canHandleJsonInvalidValue() throws IOException {

		String invalidJson = "{ \"countryCode\" : 1 }";
		String formatErrorMessage = "Invalid Value";
		JsonParser parser  = new JsonFactory().createParser(invalidJson);
		InvalidFormatException ifx = new InvalidFormatException(parser,
				formatErrorMessage, 1, String.class);

		String errorMessage = "Invalid Input";
		HttpMessageNotReadableException ex = new HttpMessageNotReadableException(
				errorMessage, ifx);

		HttpHeaders headers = Mockito.mock(HttpHeaders.class);
		HttpStatus status = HttpStatus.BAD_REQUEST;
		WebRequest request = Mockito.mock(WebRequest.class);

		ResponseEntity<Object> handleHttpMessageNotReadable = exceptionHandler
				.handleHttpMessageNotReadable(ex, headers, status, request);

		assertNotNull(handleHttpMessageNotReadable);
		assertTrue(handleHttpMessageNotReadable.getBody() instanceof ApiError);
	}

	@Test
	public void canHandleArgumentMisMatch() {

		Object value = "test";
		MethodParameter param = new MethodParameter(DummyType.class.getMethods()[0], -1);
		Throwable cause = new Throwable("Invalid argument");
		MethodArgumentTypeMismatchException ex = new MethodArgumentTypeMismatchException(value,
				DummyType.class, "myrequired", param, cause);

		ResponseEntity<Object> handleHttpMessageNotReadable = exceptionHandler
				.handleMethodArgumentTypeMismatch(ex);

		assertNotNull(handleHttpMessageNotReadable);
		assertTrue(handleHttpMessageNotReadable.getBody() instanceof ApiError);
	}

	@Test
	public void canHandleIllegalArgument() {
		
		IllegalArgumentException ex = new IllegalArgumentException("Illegal argument");

		ResponseEntity<Object> handleHttpMessageNotReadable = exceptionHandler.handleIllegalArgument(ex);

		assertNotNull(handleHttpMessageNotReadable);
		assertTrue(handleHttpMessageNotReadable.getBody() instanceof ApiError);
	}

	@Test
	public void canHandleServerError() {
		NullPointerException ex = new NullPointerException("Null pointer");

		ResponseEntity<Object> handleInternalServerError = exceptionHandler.handleInternalServerError(ex);

		assertNotNull(handleInternalServerError);
		assertTrue(handleInternalServerError.getBody() instanceof ApiError);
	}

	@Test
	public void canHandleServiceException() {
		ApiServiceException ex = new ApiServiceException("API Service Exception");

		ResponseEntity<Object> handleInternalServerError = exceptionHandler.handleInternalServerError(ex);

		assertNotNull(handleInternalServerError);
		assertTrue(handleInternalServerError.getBody() instanceof ApiError);
	}

	@Test
	public void canHandleIllegalArgumentException() {
 		IllegalArgumentException cause = new IllegalArgumentException("Illegal Argument Exception");
		Exception ex = new 	Exception(cause);

		ResponseEntity<Object> illegalArgumentException = exceptionHandler.handleInternalServerError(ex);

		assertNotNull(illegalArgumentException);
		assertTrue(illegalArgumentException.getBody() instanceof ApiError);
	}

	static class DummyType {
		public void testMethod() {
		}
	}
}
