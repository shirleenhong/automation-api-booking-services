package com.cwt.bpg.cbt.exchange.order.model;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ProductEnum {
	
	CONSOLIDATOR_TICKET("02"),
	LIMOUSINE("07"),
	CAR_TRANSFER("07"),
	HOTEL_PREPAID("16"),
	TRANSACTION_FEE("35"),
	FERRY("14"),
	TRAIN("15"),
	VISA_PROCESSING("06"),
	VISA_COST("06"),
	CLIENT_CARD("91");

	@JsonValue
	private final String code;

	ProductEnum(String code) {
		this.code = code;
	}
	
	public String getCode() {
		return code;
	}
}
