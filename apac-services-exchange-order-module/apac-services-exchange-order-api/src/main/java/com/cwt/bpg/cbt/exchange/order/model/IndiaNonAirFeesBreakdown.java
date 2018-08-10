package com.cwt.bpg.cbt.exchange.order.model;

import java.math.BigDecimal;

public class IndiaNonAirFeesBreakdown extends NonAirFeesBreakdown {

	private static final long serialVersionUID = 3847345778240245241L;

	private BigDecimal discount;
	private BigDecimal grossSellingPrice;
	private boolean noMerchantFee;
	private boolean clientExempt;
	
	public BigDecimal getDiscount() {
		return discount;
	}

	public void setDiscount(BigDecimal discount) {
		this.discount = discount;
	}
	
	public BigDecimal getGrossSellingPrice() {
		return grossSellingPrice;
	}

	public void setGrossSellingPrice(BigDecimal grossSellingPrice) {
		this.grossSellingPrice = grossSellingPrice;
	}

	public boolean isNoMerchantFee() {
		return noMerchantFee;
	}

	public void setNoMerchantFee(boolean noMerchantFee) {
		this.noMerchantFee = noMerchantFee;
	}

	public boolean isClientExempt() {
		return clientExempt;
	}

	public void setClientExempt(boolean clientExempt) {
		this.clientExempt = clientExempt;
	}
}
