package com.cwt.bpg.cbt.exchange.order.model;


import java.io.Serializable;


import io.swagger.annotations.ApiModelProperty;

public class FormOfPayment implements Serializable {

    private static final long serialVersionUID = 1850641277169808339L;

    @ApiModelProperty(allowableValues = "CX, CC, INV, BTC")
	private FopType fopType;
    
    private String creditCardNumber;

	public FopType getFopType() {
		return fopType;
	}

	public void setFopType(FopType fopType) {
		this.fopType = fopType;
	}

	public String getCreditCardNumber() {
		return creditCardNumber;
	}

	public void setCreditCardNumber(String creditCardNumber) {
		this.creditCardNumber = creditCardNumber;
	}
}
