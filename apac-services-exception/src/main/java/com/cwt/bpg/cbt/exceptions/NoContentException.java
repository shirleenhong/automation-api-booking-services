package com.cwt.bpg.cbt.exceptions;

/**
 * will give http status 204 - No Content
 */
public class NoContentException extends ServiceException {

	private static final long serialVersionUID = -1939268071824789876L;

	public NoContentException(String statusText) {
		super(204, statusText);
	}
}
