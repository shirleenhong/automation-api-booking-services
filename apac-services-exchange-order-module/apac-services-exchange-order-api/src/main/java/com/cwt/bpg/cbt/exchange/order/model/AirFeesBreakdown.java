package com.cwt.bpg.cbt.exchange.order.model;

import java.math.BigDecimal;

public class AirFeesBreakdown extends FeesBreakdown {

	private static final long serialVersionUID = 8544229065892237666L;

	private BigDecimal totalSellingFare;
	private BigDecimal nettCost;
	private BigDecimal sellingPrice;
	private BigDecimal merchantFee;
	private BigDecimal discount;
	private BigDecimal nettFare;

	public AirFeesBreakdown() {
		super();
	}

	public BigDecimal getTotalSellingFare() {
		return totalSellingFare;
	}

	public void setTotalSellingFare(BigDecimal totalSellingFare) {
		this.totalSellingFare = totalSellingFare;
	}

	public BigDecimal getNettCost() {
		return nettCost;
	}

	public void setNettCost(BigDecimal nettCost) {
		this.nettCost = nettCost;
	}

	public BigDecimal getSellingPrice() {
		return sellingPrice;
	}

	public BigDecimal getMerchantFee() {
		return merchantFee;
	}

	public void setMerchantFee(BigDecimal merchantFee) {
		this.merchantFee = merchantFee;
	}

	public void setSellingPrice(BigDecimal sellingPrice) {
		this.sellingPrice = sellingPrice;
	}

	public BigDecimal getDiscount() {
		return discount;
	}

	public void setDiscount(BigDecimal discount) {
		this.discount = discount;
	}

	public BigDecimal getNettFare() {
		return nettFare;
	}

	public void setNettFare(BigDecimal nettFare) {
		this.nettFare = nettFare;
	}
}
