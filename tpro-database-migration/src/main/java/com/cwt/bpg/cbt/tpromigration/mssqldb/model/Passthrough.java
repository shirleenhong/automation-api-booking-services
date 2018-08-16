package com.cwt.bpg.cbt.tpromigration.mssqldb.model;

import java.math.BigDecimal;

public class Passthrough {

	private String bookingClass;

	private String airlineCode;

	private String airlineDescription;

	private String ccVendorCode;

	private String ccVendorName;

	private String ccType;

	private String passthroughType;

	private String passthrough;

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

	public String getAirlineDescription() {
		return airlineDescription;
	}

	public void setAirlineDescription(String airlineDescription) {
		this.airlineDescription = airlineDescription;
	}

	public String getCcVendorCode() {
		return ccVendorCode;
	}

	public void setCcVendorCode(String ccVendorCode) {
		this.ccVendorCode = ccVendorCode;
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

	public String getPassthrough() {
		return passthrough;
	}

	public void setPassthrough(String passthrough) {
		this.passthrough = passthrough;
	}

	public String getClientAccountNumber() {
		return clientAccountNumber;
	}

	public void setClientAccountNumber(String clientAccountNumber) {
		this.clientAccountNumber = clientAccountNumber;
	}
}
