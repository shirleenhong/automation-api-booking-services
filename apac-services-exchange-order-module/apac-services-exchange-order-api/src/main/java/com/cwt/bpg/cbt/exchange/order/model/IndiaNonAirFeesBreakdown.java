package com.cwt.bpg.cbt.exchange.order.model;

import java.math.BigDecimal;

public class IndiaNonAirFeesBreakdown extends NonAirFeesBreakdown {

	private static final long serialVersionUID = 3847345778240245241L;

	private BigDecimal discount;
	
	public BigDecimal getDiscount() {
		return discount;
	}
	public void setDiscount(BigDecimal discount) {
		this.discount = discount;
	}
}
