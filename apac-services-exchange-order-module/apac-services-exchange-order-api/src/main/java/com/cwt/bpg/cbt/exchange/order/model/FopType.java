package com.cwt.bpg.cbt.exchange.order.model;

import com.fasterxml.jackson.annotation.JsonValue;

public enum FopType {

	CWT("CX"),
	CREDIT_CARD("CC"), 
	INVOICE("INV"),
	BTC("BTC");

	@JsonValue
	private final String code;

	FopType(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}

}
