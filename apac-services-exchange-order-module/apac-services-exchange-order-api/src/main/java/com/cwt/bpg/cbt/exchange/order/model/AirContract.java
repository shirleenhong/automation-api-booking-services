package com.cwt.bpg.cbt.exchange.order.model;

import com.cwt.bpg.cbt.utils.ObjectIdSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Id;

import java.io.Serializable;

public class AirContract implements Serializable {
	
	private static final long serialVersionUID = -4494953673618734452L;

	@Id
	@JsonSerialize(using = ObjectIdSerializer.class)
	private ObjectId id;

	private String clientAccountNumber;
	
	private String countryCode;
	
	private String airlineCode;
	
	private String fopCode;

	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
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

	public String getAirlineCode() {
		return airlineCode;
	}

	public void setAirlineCode(String airlineCode) {
		this.airlineCode = airlineCode;
	}

	public String getFopCode() {
		return fopCode;
	}

	public void setFopCode(String fopCode) {
		this.fopCode = fopCode;
	}
}
