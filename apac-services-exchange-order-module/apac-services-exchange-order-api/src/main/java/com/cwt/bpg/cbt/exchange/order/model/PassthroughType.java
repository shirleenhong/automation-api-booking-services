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
}
