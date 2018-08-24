package com.cwt.bpg.cbt.exchange.order.model;


import java.io.Serializable;

import javax.validation.Valid;

import com.cwt.bpg.cbt.exchange.order.model.validator.CcNotNullOnFopType;

import io.swagger.annotations.ApiModelProperty;

@CcNotNullOnFopType(fopTypes ={"CC","CX"})
public class FormOfPayment implements Serializable {

    private static final long serialVersionUID = 1850641277169808339L;

	@ApiModelProperty(allowableValues = "CX, CC, INV, BTC")
	private FopType fopType;

	@Valid
    private CreditCard creditCard;

	public FopType getFopType() {
		return fopType;
	}

	public void setFopType(FopType fopType) {
		this.fopType = fopType;
	}

	public CreditCard getCreditCard() {
		return creditCard;
	}

	public void setCreditCard(CreditCard creditCard) {
		this.creditCard = creditCard;
	}
}
