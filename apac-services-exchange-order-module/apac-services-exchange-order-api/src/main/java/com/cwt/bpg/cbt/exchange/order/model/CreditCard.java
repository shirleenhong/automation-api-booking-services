package com.cwt.bpg.cbt.exchange.order.model;

import java.io.Serializable;

import org.hibernate.validator.constraints.NotEmpty;

import io.swagger.annotations.ApiModelProperty;

public class CreditCard implements Serializable {

    private static final long serialVersionUID = 4581832344361573326L;
    
    @NotEmpty
    @ApiModelProperty(required = true)
    private String ccType;
    
    @NotEmpty
    @ApiModelProperty(required = true)
    private String ccNumber;
    
    @NotEmpty
    @ApiModelProperty(required = true)
    private String expiryDate;
    
    private String preference;    

    public String getCcType() {
        return ccType;
    }

    public void setCcType(String ccType) {
        this.ccType = ccType;
    }

    public String getCcNumber() {
        return ccNumber;
    }

    public void setCcNumber(String ccNumber) {
        this.ccNumber = ccNumber;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

	public String getPreference() {
		return preference;
	}

	public void setPreference(String preference) {
		this.preference = preference;
	}
    
}
