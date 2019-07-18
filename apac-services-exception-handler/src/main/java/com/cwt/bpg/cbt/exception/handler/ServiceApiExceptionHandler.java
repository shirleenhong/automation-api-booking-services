package com.cwt.bpg.cbt.exception.handler;

import java.util.*;

import com.cwt.bpg.cbt.exceptions.FileUploadException;
import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.util.CollectionUtils;
import org.springframework.util.LinkedCaseInsensitiveMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.cwt.bpg.cbt.exceptions.ServiceException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;

@ControllerAdvice
public class ServiceApiExceptionHandler extends ResponseEntityExceptionHandler {

	private static final String INVALID_INPUT = "Invalid Input";

    @ExceptionHandler({ MethodArgumentTypeMismatchException.class })
	public ResponseEntity<Object> handleMethodArgumentTypeMismatch(
			MethodArgumentTypeMismatchException ex) {

		logger.error("Server caught an exception: {}", ex);

		String error = ex.getName() + " should be of type "
				+ ex.getRequiredType().getName();

		ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(),
				error);

		return new ResponseEntity<>(apiError, new HttpHeaders(),
				apiError.getStatus());
	}

	@ExceptionHandler({ IllegalArgumentException.class })
	public ResponseEntity<Object> handleIllegalArgument(
			IllegalArgumentException ex) {

		logger.error("Server caught an exception: {}", ex);

		String error = ex.getMessage();

		ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(),
				error);

		return new ResponseEntity<>(apiError, new HttpHeaders(),
				apiError.getStatus());
	}

	@ExceptionHandler({ FileUploadException.class })
	public ResponseEntity<Object> handleFileUploadException(FileUploadException ex) {

    	logger.error("Validation error caught during upload");

    	ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getMessage(), ex.getErrors());

    	return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
	}

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		logger.error("Server caught an exception: {}", ex);

		Throwable mostSpecificCause = ex.getMostSpecificCause();
		StringBuilder error = new StringBuilder();

		if (mostSpecificCause instanceof JsonParseException) {
			error.append(((JsonParseException)mostSpecificCause).getMessage());
		}
		else if (mostSpecificCause instanceof InvalidFormatException) {
			InvalidFormatException x = (InvalidFormatException) mostSpecificCause;
			error.append("[").append(x.getValue()).append("] should be of type [").append(x.getTargetType()
					.getName().split("\\.")[x.getTargetType().getName().split("\\.").length - 1]).append("]");
		}
		
		ApiError apiError;
		
		if (error.length() > 0) {
			apiError = new ApiError(HttpStatus.BAD_REQUEST, INVALID_INPUT, error.toString());
		}
		else {
			apiError = new ApiError(HttpStatus.BAD_REQUEST, INVALID_INPUT);
		}

		return handleExceptionInternal(ex, apiError, headers, apiError.getStatus(), request);
	}

	@Override
	public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		logger.error("Server caught an exception: {}", ex);
		ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, INVALID_INPUT);
		apiError.setErrors(getMessages(ex.getBindingResult()));
		return handleExceptionInternal(ex, apiError, headers, apiError.getStatus(), request);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<Object> handleInternalServerError(Exception ex) {
		logger.error("Server caught an exception: {}", ex);

		if (ex instanceof ServiceException) {
			ServiceException internalException = ((ServiceException) ex);
			Optional<Map<String, List<String>>> optionalHeaders = Optional.ofNullable(internalException.getHeaders());
			HttpHeaders headers = new HttpHeaders();
			headers.putAll(optionalHeaders.orElse(new LinkedCaseInsensitiveMap<List<String>>(8, Locale.ENGLISH)));

			ApiError apiError = new ApiError(HttpStatus.valueOf(internalException.getStatusCode()), "There were exception found during the process.");
			return new ResponseEntity<>(apiError, headers, apiError.getStatus());
		}

		if(ex.getCause() instanceof IllegalArgumentException){
			String error = ex.getCause().getMessage();
			ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, error,error);
			return new ResponseEntity<>(apiError, new HttpHeaders(),apiError.getStatus());
		}

		ApiError apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error");

		return new ResponseEntity<>(apiError, apiError.getStatus());
	}

	private List<String> getMessages(BindingResult bindingResult) {
		Set<String> errors = new HashSet<>();
		if(CollectionUtils.isEmpty(bindingResult.getAllErrors())) {
			return Collections.emptyList();
		}
		for (ObjectError objectError : bindingResult.getAllErrors()) {
			errors.add(objectError.getDefaultMessage());
		}
		return new ArrayList<>(errors);
	}
}
