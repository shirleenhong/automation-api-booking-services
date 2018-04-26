package com.cwt.bpg.cbt.calculator.config;

public enum Country {
	
	HONG_KONG("HK"),
	SINGAPORE("SG"),
	AUSTRALIA("AU"),
	NEW_ZEALAND("NZ");
	
	private final String code;

	private Country(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}

}
