package com.cwt.bpg.cbt.exchange.order.exception;

import com.cwt.bpg.cbt.exceptions.NoContentException;

public class ExchangeOrderNoContentException extends NoContentException {

	private static final long serialVersionUID = 1484272599306361299L;
	
	public ExchangeOrderNoContentException(String message) {
		super(message);
	}
}
