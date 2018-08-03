package com.cwt.bpg.cbt.exchange.order.model;

import com.fasterxml.jackson.annotation.JsonValue;

public enum FopTypes
{

	CWT("CX4"),
	CREDIT_CARD("CC"), 
	INVOICE("INV"),
	BTC("BTC");

	@JsonValue
	private final String code;

	FopTypes(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}

}
