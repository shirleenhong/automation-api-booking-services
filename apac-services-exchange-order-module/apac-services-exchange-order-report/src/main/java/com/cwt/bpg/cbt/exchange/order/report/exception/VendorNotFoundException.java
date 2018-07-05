package com.cwt.bpg.cbt.exchange.order.report.exception;

public class VendorNotFoundException extends Exception {

	private static final long serialVersionUID = 1089698962771394152L;

	public VendorNotFoundException(String statusText) {
		super(statusText);
	}
}
