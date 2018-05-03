package com.cwt.bpg.cbt.exchange.order.model;

public enum ClientTypes {

	DU("DU"), 
	DB("DB"), 
	MN("MN"),
	TF("TF"),
	MG("MG"),
	TP("TP");

	private final String code;

	private ClientTypes(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}
}
