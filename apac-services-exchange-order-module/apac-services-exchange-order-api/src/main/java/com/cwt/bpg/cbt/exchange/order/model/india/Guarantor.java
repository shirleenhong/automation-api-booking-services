package com.cwt.bpg.cbt.exchange.order.model.india;

import com.cwt.bpg.cbt.exchange.order.model.CreditCard;

public class Guarantor {

	private String type;

	private CreditCard creditCard;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public CreditCard getCreditCard() {
		return creditCard;
	}

	public void setCreditCard(CreditCard creditCard) {
		this.creditCard = creditCard;
	}

}
