package com.cwt.bpg.cbt.exchange.order.model;

public enum FopTypes
{

	CWT("CX4"),
	CREDIT_CARD("CC"), 
	INVOICE("INV");

	private final String code;

	FopTypes(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}

}
