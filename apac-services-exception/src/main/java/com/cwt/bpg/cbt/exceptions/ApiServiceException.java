package com.cwt.bpg.cbt.exceptions;

/**
 *	Generic exception that we identify as internal server exception 
 *
 */
public class ApiServiceException extends ServiceException {

	private static final long serialVersionUID = -1446802675164075852L;

	public static final int STATUS_CODE = 500;

	public ApiServiceException(String statusText) {
		super(STATUS_CODE, statusText);
	}

	public ApiServiceException(String message, Throwable cause) {
		super(STATUS_CODE, message, cause);
	}
}
