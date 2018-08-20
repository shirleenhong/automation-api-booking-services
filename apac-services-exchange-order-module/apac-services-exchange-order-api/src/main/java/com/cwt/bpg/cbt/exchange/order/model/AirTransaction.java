package com.cwt.bpg.cbt.exchange.order.model;

import java.io.Serializable;

import org.mongodb.morphia.annotations.Entity;

@Entity(value = "airTransactions", noClassnameStored = true)
public class AirTransaction implements Serializable {

	private static final long serialVersionUID = 5943172847055937530L;

	private String airlineDescription;

	private String ccVendorName;

	private String ccType;

	private String passthroughType;
	
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

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getClientAccountNumber() {
		return clientAccountNumber;
	}

	public void setClientAccountNumber(String clientAccountNumber) {
		this.clientAccountNumber = clientAccountNumber;
	}

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
