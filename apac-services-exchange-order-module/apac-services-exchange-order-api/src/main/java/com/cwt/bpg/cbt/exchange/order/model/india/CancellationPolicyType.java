package com.cwt.bpg.cbt.exchange.order.model.india;

import com.fasterxml.jackson.annotation.JsonValue;

public enum CancellationPolicyType {
	DURATION("Duration"),
    SPECIAL_RATE("SpecialRate"),
    OTHERS("Others");

	@JsonValue
	private final String code;

	CancellationPolicyType(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}
}
