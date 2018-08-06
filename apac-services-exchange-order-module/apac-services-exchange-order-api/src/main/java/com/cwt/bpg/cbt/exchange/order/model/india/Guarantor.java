package com.cwt.bpg.cbt.exchange.order.model.india;

import java.io.Serializable;

import com.cwt.bpg.cbt.exchange.order.model.CreditCard;

public class Guarantor implements Serializable {

	private static final long serialVersionUID = -4416501653792392878L;

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
