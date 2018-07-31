package com.cwt.bpg.cbt.exchange.order.model.india;

import java.math.BigDecimal;

public class ConsolInfo {

	private String tourCode;
	
	private BigDecimal thresholdAmount;

	public String getTourCode() {
		return tourCode;
	}

	public void setTourCode(String tourCode) {
		this.tourCode = tourCode;
	}

	public BigDecimal getThresholdAmount() {
		return thresholdAmount;
	}

	public void setThresholdAmount(BigDecimal thresholdAmount) {
		this.thresholdAmount = thresholdAmount;
	}

}
