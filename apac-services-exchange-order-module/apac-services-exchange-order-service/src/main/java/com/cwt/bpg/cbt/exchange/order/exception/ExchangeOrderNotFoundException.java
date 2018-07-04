package com.cwt.bpg.cbt.exchange.order.exception;

public class ExchangeOrderNotFoundException extends ExchangeOrderException {

	private static final long serialVersionUID = 1484272599306361299L;
	
	public ExchangeOrderNotFoundException(String message) {
		super(message);
	}

	public ExchangeOrderNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

}
