package com.cwt.bpg.cbt.exchange.order.model;

import java.math.BigDecimal;

public class NonAirFeesBreakdown extends FeesBreakdown {

	private static final long serialVersionUID = 3847345778240245241L;

	private BigDecimal totalSellingPrice;
	private BigDecimal gstAmount;
	private BigDecimal merchantFee;
	private BigDecimal nettCostGst;
	private BigDecimal grossSellingPrice;

	public BigDecimal getTotalSellingPrice() {
		return totalSellingPrice;
	}

	public void setTotalSellingPrice(BigDecimal totalSellingPrice) {
		this.totalSellingPrice = totalSellingPrice;
	}

	public BigDecimal getGstAmount() {
		return gstAmount;
	}

	public void setGstAmount(BigDecimal gstAmount) {
		this.gstAmount = gstAmount;
	}

	public BigDecimal getMerchantFee() {
		return merchantFee;
	}

	public void setMerchantFee(BigDecimal merchantFee) {
		this.merchantFee = merchantFee;
	}

	public BigDecimal getNettCostGst() {
		return nettCostGst;
	}

	public void setNettCostGst(BigDecimal nettCostGst) {
		this.nettCostGst = nettCostGst;
	}

	public BigDecimal getGrossSellingPrice() {
		return grossSellingPrice;
	}

	public void setGrossSellingPrice(BigDecimal grossSellingPrice) {
		this.grossSellingPrice = grossSellingPrice;
	}
}
