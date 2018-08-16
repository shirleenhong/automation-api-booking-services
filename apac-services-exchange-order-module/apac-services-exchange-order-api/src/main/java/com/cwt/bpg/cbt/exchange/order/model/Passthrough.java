package com.cwt.bpg.cbt.exchange.order.model;

import java.io.Serializable;

public class Passthrough extends BasePassthrough implements Serializable {

	private static final long serialVersionUID = 5943172847055937530L;

	private String airlineDescription;

	private String ccVendorName;

	private String ccType;

	private String passthroughType;

	public String getAirlineDescription() {
		return airlineDescription;
	}

	public void setAirlineDescription(String airlineDescription) {
		this.airlineDescription = airlineDescription;
	}

	public String getCcVendorName() {
		return ccVendorName;
	}

	public void setCcVendorName(String ccVendorName) {
		this.ccVendorName = ccVendorName;
	}

	public String getCcType() {
		return ccType;
	}

	public void setCcType(String ccType) {
		this.ccType = ccType;
	}

	public String getPassthroughType() {
		return passthroughType;
	}

	public void setPassthroughType(String passthroughType) {
		this.passthroughType = passthroughType;
	}
}
