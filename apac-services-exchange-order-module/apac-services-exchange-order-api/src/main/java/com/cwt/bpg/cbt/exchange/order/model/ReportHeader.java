package com.cwt.bpg.cbt.exchange.order.model;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

import io.swagger.annotations.ApiModelProperty;

@Entity(value = "reportHeaders", noClassnameStored = true)
public class ReportHeader implements Serializable {

    private static final long serialVersionUID = -1835330460259508098L;
    
    @Id
    @NotEmpty
    @ApiModelProperty(value = "Country code")
    private String countryCode;

    @ApiModelProperty(required = true, value = "Header Name")
    private String companyName;

    @NotEmpty
    @ApiModelProperty(required = true, value = "Header Address")
    private String address;

    @ApiModelProperty(value = "Header Phone Number")
    private String phoneNumber;

    @ApiModelProperty(value = "Header Fax Number")
    private String faxNumber;
    

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

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

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}
}
