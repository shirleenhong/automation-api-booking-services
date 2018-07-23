package com.cwt.bpg.cbt.exchange.order.model;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ContactInfoType {
	EMAIL("Email"),
	Fax("Fax"),
	PHONE("Phone");

	@JsonValue
	private final String value;

	private ContactInfoType(String value) {
		this.value = value;
	}
}
