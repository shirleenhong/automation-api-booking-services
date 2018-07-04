package com.cwt.bpg.cbt.exchange.order.model;

import java.io.Serializable;

import org.hibernate.validator.constraints.NotEmpty;

import io.swagger.annotations.ApiModelProperty;

public class Header implements Serializable {

	private static final long serialVersionUID = 2269819571493866673L;
	
    @NotEmpty
    @ApiModelProperty(required = true, value = "Header Address")
    private String address;
    
    @ApiModelProperty(value = "Header Phone Number")
    private String phoneNumber;
    
    @ApiModelProperty(value = "Header Fax Number")
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
