package com.cwt.bpg.cbt.air.transaction.model;

import java.io.Serializable;
import java.util.List;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.*;

import com.cwt.bpg.cbt.utils.ObjectIdSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@Entity(value = "airTransactions", noClassnameStored = true)
@Indexes(@Index(fields = @Field("id")))
public class AirTransaction implements Serializable {

	private static final long serialVersionUID = 5943172847055937530L;

	@Id
	@JsonSerialize(using = ObjectIdSerializer.class)
	private ObjectId id;

	private String airlineDescription;

	private String ccVendorName;

	private String ccType;

	private PassthroughType passthroughType;
	
	private List<String> bookingClasses;

	private String airlineCode;

	private String ccVendorCode;
	
	private String countryCode;

	private String clientAccountNumber;

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

	public PassthroughType getPassthroughType() {
		return passthroughType;
	}

	public void setPassthroughType(PassthroughType passthroughType) {
		this.passthroughType = passthroughType;
	}
}
