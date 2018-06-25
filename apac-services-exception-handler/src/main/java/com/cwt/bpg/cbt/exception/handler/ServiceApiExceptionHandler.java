package com.cwt.bpg.cbt.exception.handler;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.mongodb.util.JSONParseException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ServiceApiExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler({ MethodArgumentTypeMismatchException.class })
	public ResponseEntity<Object> handleMethodArgumentTypeMismatch(
			MethodArgumentTypeMismatchException ex, WebRequest request) {

		String error = ex.getName() + " should be of type "
				+ ex.getRequiredType().getName();

		ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(),
				error);

		return new ResponseEntity<>(apiError, new HttpHeaders(),
				apiError.getStatus());
	}

	@ExceptionHandler({ IllegalArgumentException.class })
	public ResponseEntity<Object> handleIllegalArgument(
			IllegalArgumentException ex, WebRequest request) {

		String error = ex.getMessage();

		ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(),
				error);

		return new ResponseEntity<>(apiError, new HttpHeaders(),
				apiError.getStatus());
	}

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(
			HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status,
			WebRequest request) {

		Throwable mostSpecificCause = ex.getMostSpecificCause();
		String error = "";

		if(mostSpecificCause instanceof InvalidFormatException){
			InvalidFormatException x = (InvalidFormatException)mostSpecificCause;
			error = "["+x.getValue()+"] should be of type ["+x.getTargetType().getName().split("\\." )[x.getTargetType().getName().split("\\." ).length-1]+"]";
		}

		if(mostSpecificCause instanceof JSONParseException || mostSpecificCause instanceof JsonParseException){
			error = "Invalid JSON";
		}

		ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, "Invalid Input", error);

		return handleExceptionInternal(ex, apiError, headers, apiError.getStatus(),
				request);
	}

	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(Exception.class)
	public ResponseEntity<Object> handleInternalServerError(Exception ex) {

		ApiError apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error", "");

		return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
	}

}
