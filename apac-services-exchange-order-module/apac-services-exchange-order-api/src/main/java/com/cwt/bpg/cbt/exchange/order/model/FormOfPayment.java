package com.cwt.bpg.cbt.exchange.order.model;

import java.io.Serializable;

public class FormOfPayment implements Serializable {

    private static final long serialVersionUID = 1850641277169808339L;

	private FopTypes fopType;

    private CreditCard creditCard;

	public FopTypes getFopType() {
		return fopType;
	}

	public void setFopType(FopTypes fopType) {
		this.fopType = fopType;
	}

	public CreditCard getCreditCard() {
		return creditCard;
	}

	public void setCreditCard(CreditCard creditCard) {
		this.creditCard = creditCard;
	}
}
