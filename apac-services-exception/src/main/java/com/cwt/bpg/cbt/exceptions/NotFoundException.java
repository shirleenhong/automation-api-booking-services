package com.cwt.bpg.cbt.exceptions;
/**
 * will give http status 404 - not found exception
 */
public class NotFoundException extends ApacServiceException {

	private static final long serialVersionUID = 2081556770053061161L;

	public NotFoundException(String statusText) {
		super(404, statusText);
	}
}
