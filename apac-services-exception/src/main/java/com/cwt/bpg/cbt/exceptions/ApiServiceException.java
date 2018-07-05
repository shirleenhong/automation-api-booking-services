package com.cwt.bpg.cbt.exceptions;

/**
 *	Generic exception that we identify as internal server exception 
 *
 */
public class ApiServiceException extends ServiceException {

	private static final long serialVersionUID = -1446802675164075852L;

	public ApiServiceException(String statusText) {
		super(500, statusText);
	}
}
