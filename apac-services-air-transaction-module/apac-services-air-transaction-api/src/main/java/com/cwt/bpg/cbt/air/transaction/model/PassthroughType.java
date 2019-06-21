package com.cwt.bpg.cbt.air.transaction.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum PassthroughType {

    AIRLINE("Airline"),
    CWT("CWT"),
    Airline("Airline");

	@JsonValue
	private final String code;

	PassthroughType(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}
	
	@JsonCreator
	public static PassthroughType fromString(String code) {
		for (PassthroughType type : PassthroughType.values()) {
		    if (type.code.equalsIgnoreCase(code)) {
				return type;
			}
		}
		return null;
	}
}
