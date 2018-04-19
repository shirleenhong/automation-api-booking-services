package com.cwt.bpg.cbt.exchange.order.model;

public enum FOPTypes {

	CX("CX"), 
	CREDIT_CARD("CC"), 
	INVOICE("INV");

	private final String code;

	private FOPTypes(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}

}
