package com.cwt.bpg.cbt.air.transaction.exception;

import com.cwt.bpg.cbt.exceptions.NoContentException;

public class AirTransactionNoContentException extends NoContentException {

	private static final long serialVersionUID = 1484272599306361299L;
	
	public AirTransactionNoContentException(String message) {
		super(message);
	}
}
