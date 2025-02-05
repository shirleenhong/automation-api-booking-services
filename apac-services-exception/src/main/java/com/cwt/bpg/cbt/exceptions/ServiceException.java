package com.cwt.bpg.cbt.exceptions;

import java.util.List;
import java.util.Map;

public class ServiceException extends Exception {

	private static final long serialVersionUID = 8468026548368036885L;

	private int statusCode;
	
	private Map<String, List<String>> headers;
	
	private String statusText;

	public ServiceException(int statusCode) {
		super();
		this.statusCode = statusCode;
	}

	public ServiceException(int statusCode, String message, Throwable cause) {
		super(message, cause);
		this.statusCode = statusCode;
	}

	public ServiceException(int statusCode, Map<String, List<String>> headers) {
		super();
		this.statusCode = statusCode;
		this.headers = headers;
	}

	public ServiceException(int statusCode, Map<String, List<String>> headers, String statusText) {
		super(statusCode + " " + statusText);
		this.statusCode = statusCode;
		this.headers = headers;
		this.statusText = statusText;
	}

	public ServiceException(int statusCode, String statusText) {
		super(statusCode + " " + statusText);
		this.statusText = statusText;
		this.statusCode = statusCode;
	}

	public final int getStatusCode() {
		return statusCode;
	}

	public final void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public final Map<String, List<String>> getHeaders() {
		return headers;
	}

	public final void setHeaders(Map<String, List<String>> headers) {
		this.headers = headers;
	}

	public final String getStatusText() {
		return statusText;
	}
}
