package com.cwt.bpg.cbt.exchange.order.model;

import java.io.Serializable;

public class AirTransactionOutput implements Serializable {

	private static final long serialVersionUID = 7448467415612367423L;
	
	private String passthroughType;

	public String getPassthroughType() {
		return passthroughType;
	}

	public void setPassthroughType(String passthroughType) {
		this.passthroughType = passthroughType;
	}
}
