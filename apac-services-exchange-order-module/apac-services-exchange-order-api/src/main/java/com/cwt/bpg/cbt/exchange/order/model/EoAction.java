package com.cwt.bpg.cbt.exchange.order.model;

import com.fasterxml.jackson.annotation.JsonValue;

public enum EoAction {
	PRINT("Print"),
	EMAIL("Email"),
	REQUEST_CHEQUE("Request Cheque");

	@JsonValue
	private final String value;

	private EoAction(String value) {
		this.value = value;
	}
}
