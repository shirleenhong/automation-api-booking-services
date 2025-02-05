package com.cwt.bpg.cbt.exchange.order.model;

import java.io.Serializable;

public class IndiaAirProductInput implements Serializable {
	
	private static final long serialVersionUID = 4262078426836854023L;
	
	private Double ot1Percent;
	private Double ot2Percent;
	private Double gstPercent;
	
	public Double getOt1Percent() {
		return ot1Percent;
	}

	public void setOt1Percent(Double ot1Percent) {
		this.ot1Percent = ot1Percent;
	}

	public Double getOt2Percent() {
		return ot2Percent;
	}

	public void setOt2Percent(Double ot2Percent) {
		this.ot2Percent = ot2Percent;
	}
	
	public Double getGstPercent() {
		return gstPercent;
	}

	public void setGstPercent(Double gstPercent) {
		this.gstPercent = gstPercent;
	}
	
}
