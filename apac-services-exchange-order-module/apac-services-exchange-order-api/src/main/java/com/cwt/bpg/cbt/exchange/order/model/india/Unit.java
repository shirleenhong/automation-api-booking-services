package com.cwt.bpg.cbt.exchange.order.model.india;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Unit {
	DAYS("Days"),
    HOURS("Hrs");

	@JsonValue
	private String code;

	Unit(String code) {
		this.code = code;
	}

    public String getCode() {
        return code;
    }

}
