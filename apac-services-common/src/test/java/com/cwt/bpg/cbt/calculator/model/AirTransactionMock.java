package com.cwt.bpg.cbt.calculator.model;

import java.io.Serializable;

public class AirTransactionMock implements Serializable {

	private static final long serialVersionUID = 5943172847055937530L;

	private String airlineCode;

	public String getAirlineCode() {
		return airlineCode;
	}

	public void setAirlineCode(String airlineCode) {
		this.airlineCode = airlineCode;
	}
}
