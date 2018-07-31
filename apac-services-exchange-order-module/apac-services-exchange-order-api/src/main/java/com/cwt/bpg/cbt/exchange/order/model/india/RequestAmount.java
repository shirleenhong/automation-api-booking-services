package com.cwt.bpg.cbt.exchange.order.model.india;

import java.math.BigDecimal;

public class RequestAmount {

	private String type;

	private String currency;;

	private BigDecimal amount;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

}
