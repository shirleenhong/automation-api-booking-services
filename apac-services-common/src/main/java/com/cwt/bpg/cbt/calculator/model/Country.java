package com.cwt.bpg.cbt.calculator.model;

public enum Country {
	
	HONG_KONG("HK"),
	SINGAPORE("SG"),
	AUSTRALIA("AU"),
	NEW_ZEALAND("NZ"),
	INDIA("IN");
	
	private final String code;

	private Country(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}

}
