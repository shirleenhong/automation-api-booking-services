package com.cwt.bpg.cbt.service.fee.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class PriceCalculationInput implements Serializable {

	private static final long serialVersionUID = 8501281316064680814L;
	
	private BigDecimal merchantFee;
	
	private BigDecimal baseFare;

	public BigDecimal getMerchantFee() {
		return merchantFee;
	}

	public void setMerchantFee(BigDecimal merchantFee) {
		this.merchantFee = merchantFee;
	}

	public BigDecimal getBaseFare() {
		return baseFare;
	}

	public void setBaseFare(BigDecimal baseFare) {
		this.baseFare = baseFare;
	}

}
