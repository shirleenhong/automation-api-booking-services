package com.cwt.bpg.cbt.exchange.order.model;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class FormOfPayment implements Serializable {

    private static final long serialVersionUID = 1850641277169808339L;

	@ApiModelProperty(allowableValues = "CX4, CC, INV, BTC")
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
