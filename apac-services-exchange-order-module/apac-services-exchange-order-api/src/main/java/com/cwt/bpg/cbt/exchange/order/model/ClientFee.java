package com.cwt.bpg.cbt.exchange.order.model;

import java.io.Serializable;

public class ClientFee implements Serializable {

	private static final long serialVersionUID = 2822589464033267930L;

	private String tripType;
	private Integer value;
	private String feeName;

	public String getTripType() {
		return tripType;
	}

	public void setTripType(String tripType) {
		this.tripType = tripType;
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

	public String getFeeName() {
		return feeName;
	}

	public void setFeeName(String feeName) {
		this.feeName = feeName;
	}
}
