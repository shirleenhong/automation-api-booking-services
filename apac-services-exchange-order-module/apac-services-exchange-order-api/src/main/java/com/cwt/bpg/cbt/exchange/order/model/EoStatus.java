package com.cwt.bpg.cbt.exchange.order.model;

import com.fasterxml.jackson.annotation.JsonValue;

public enum EoStatus {
	NEW("New"),
	PENDING("Pending"),
	COMPLETED("Completed");

	@JsonValue
	private final String value;

	private EoStatus(String value) {
		this.value = value;
	}
}
