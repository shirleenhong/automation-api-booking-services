package com.cwt.bpg.cbt.exchange.order.model;

import com.fasterxml.jackson.annotation.JsonValue;

public enum FopType {

	CWT("CX"),
	CREDIT_CARD("CC"), 
	INV("INV"),
	BTC("BTC"),
	CASH("CASH"),
	NONREF("NONREF"),
	INVOICE("INVOICE");

	@JsonValue
	private final String code;

	FopType(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}

}
