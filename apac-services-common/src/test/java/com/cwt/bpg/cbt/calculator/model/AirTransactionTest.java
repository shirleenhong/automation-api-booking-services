package com.cwt.bpg.cbt.calculator.model;

import java.io.Serializable;
import java.util.List;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Field;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Index;
import org.mongodb.morphia.annotations.Indexes;

import com.cwt.bpg.cbt.utils.ObjectIdSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@Entity(value = "airTransactions", noClassnameStored = true)
@Indexes(@Index(fields = @Field("id")))
public class AirTransactionTest implements Serializable {

	private static final long serialVersionUID = 5943172847055937530L;

	@Id
	@JsonSerialize(using = ObjectIdSerializer.class)
	private ObjectId id;

	private String airlineDescription;

	private String ccVendorName;

	private String ccType;
	
	private List<String> bookingClasses;

	private String airlineCode;

	private String ccVendorCode;
	
	private String countryCode;

	private String clientAccountNumber;
	
	public AirTransactionTest() {
	}
	
	public AirTransactionTest(AirTransactionTest airTransaction) {
		this.id = airTransaction.id;
		this.airlineDescription = airTransaction.airlineDescription;
		this.ccVendorName = airTransaction.ccVendorName;
		this.ccType = airTransaction.ccType;
		this.bookingClasses = airTransaction.bookingClasses;
		this.airlineCode = airTransaction.airlineCode;
		this.ccVendorCode = airTransaction.ccVendorCode;
		this.countryCode = airTransaction.countryCode;
		this.clientAccountNumber = airTransaction.clientAccountNumber;
	}
	
	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
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

	public List<String> getBookingClasses() {
		return bookingClasses;
	}

	public void setBookingClasses(List<String> bookingClasses) {
		this.bookingClasses = bookingClasses;
	}
}
