package com.cwt.bpg.cbt.exchange.order.model;

import java.io.Serializable;

public class AirTransactionOutput implements Serializable {

	private static final long serialVersionUID = 7448467415612367423L;
	
	private PassthroughType passthroughType;

	public PassthroughType getPassthroughType() {
		return passthroughType;
	}

	public void setPassthroughType(PassthroughType passthroughType) {
		this.passthroughType = passthroughType;
	}
}
