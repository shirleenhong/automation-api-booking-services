package com.cwt.bpg.cbt.exchange.order.model;

import com.fasterxml.jackson.annotation.JsonValue;

public enum PassthroughType {

    AIRLINE("Airline"),
    CWT("CWT");

	@JsonValue
	private final String code;

	PassthroughType(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}
	
	public static PassthroughType fromString(String code) {
		for (PassthroughType type : PassthroughType.values()) {
			if (type.code.equalsIgnoreCase(code)) {
				return type;
			}
		}
		return null;
	}
}
