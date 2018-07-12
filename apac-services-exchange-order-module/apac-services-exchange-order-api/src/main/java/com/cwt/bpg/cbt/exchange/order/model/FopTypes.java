package com.cwt.bpg.cbt.exchange.order.model;

@SuppressWarnings("unused")
public enum FopTypes
{

	CWT("CX"), 
	CREDIT_CARD("CC"), 
	INVOICE("INV"),
	CASH("CASH");

	private final String code;

	FopTypes(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}

}
