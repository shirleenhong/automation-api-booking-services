package com.cwt.bpg.cbt.exchange.order.model;

public enum PricingName {

	TRANSACTION_FEE(20),
	TF_ON_FULL_FARE(21),
	TF_ON_NETT_FARE(22),
	TF_ON_GROSS_FARE(23),
	TF_ON_FARE(24),
	TF_ON_BASIC(25),
	NO_FEE(26),
	TF_PLUS_VAT(27),
	TF_REBATE(28),
	NO_FEE_WITH_DISCOUNT(29),
	TF_ON_BASE_AND_YQ(30);

	private final int id;

	PricingName(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}
}
