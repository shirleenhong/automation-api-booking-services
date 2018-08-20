package com.cwt.bpg.cbt.exchange.order.model;

import java.io.Serializable;

public class AirTransactionInput implements Serializable {

	private static final long serialVersionUID = 5943172847055937530L;

	private String bookingClass;

	private String airlineCode;

	private String ccVendorCode;
	
	private String countryCode;

	private String clientAccountNumber;

	public String getBookingClass() {
		return bookingClass;
	}

	public void setBookingClass(String bookingClass) {
		this.bookingClass = bookingClass;
	}

	public String getAirlineCode() {
		return airlineCode;
	}

	public void setAirlineCode(String airlineCode) {
		this.airlineCode = airlineCode;
	}

	public String getCcVendorCode() {
		return ccVendorCode;
	}

	public void setCcVendorCode(String ccVendorCode) {
		this.ccVendorCode = ccVendorCode;
	}

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
}
