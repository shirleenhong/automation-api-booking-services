package com.cwt.bpg.cbt.exchange.order.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class AirFeesBreakdown extends FeesBreakdown implements Serializable {
		
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8544229065892237666L;

	public AirFeesBreakdown() {
	}

	private BigDecimal totalSellingFare;
	private BigDecimal nettCostInEO;
	private BigDecimal sellingPrice;
	private BigDecimal discount;
	private BigDecimal nettFare;

	public BigDecimal getTotalSellingFare() {
		return totalSellingFare;
	}
	public void setTotalSellingFare(BigDecimal totalSellingFare) {
		this.totalSellingFare = totalSellingFare;
	}
	public BigDecimal getNettCostInEO() {
		return nettCostInEO;
	}
	public void setNettCostInEO(BigDecimal nettCostInEO) {
		this.nettCostInEO = nettCostInEO;
	}
	public BigDecimal getSellingPrice() {
		return sellingPrice;
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
