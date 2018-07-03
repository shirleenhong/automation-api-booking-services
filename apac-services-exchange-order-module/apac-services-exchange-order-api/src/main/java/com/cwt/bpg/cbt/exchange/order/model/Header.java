package com.cwt.bpg.cbt.exchange.order.model;

import java.io.Serializable;

import org.bson.types.ObjectId;
import org.hibernate.validator.constraints.NotEmpty;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Field;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Index;
import org.mongodb.morphia.annotations.Indexes;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import io.swagger.annotations.ApiModelProperty;

public class Header implements Serializable {

	private static final long serialVersionUID = 2269819571493866673L;
	
    @NotEmpty
    @ApiModelProperty(required = true, value = "Header Address")
    private String address;
    
    @ApiModelProperty(required = false, value = "Header Phone Number")
    private String phoneNumber;
    
    @ApiModelProperty(required = false, value = "Header Fax Number")
	private String faxNumber;

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getFaxNumber() {
		return faxNumber;
	}

	public void setFaxNumber(String faxNumber) {
		this.faxNumber = faxNumber;
	}
}
