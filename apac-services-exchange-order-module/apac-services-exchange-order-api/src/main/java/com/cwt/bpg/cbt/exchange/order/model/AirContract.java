package com.cwt.bpg.cbt.exchange.order.model;

import java.io.Serializable;

public class AirContract implements Serializable {
	
	private static final long serialVersionUID = -4494953673618734452L;
	
	private String clientAccountNumber;
	
	private String countryCode;
	
	private String carrier;
	
	private String fopCode;

	public String getClientAccountNumber() {
		return clientAccountNumber;
	}

	public void setClientAccountNumber(String clientAccountNumber) {
		this.clientAccountNumber = clientAccountNumber;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getCarrier() {
		return carrier;
	}

	public void setCarrier(String carrier) {
		this.carrier = carrier;
	}

	public String getFopCode() {
		return fopCode;
	}

	public void setFopCode(String fopCode) {
		this.fopCode = fopCode;
	}
	
}
