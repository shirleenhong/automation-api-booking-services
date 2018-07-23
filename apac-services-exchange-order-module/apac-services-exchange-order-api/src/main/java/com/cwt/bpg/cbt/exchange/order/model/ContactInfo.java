package com.cwt.bpg.cbt.exchange.order.model;

import java.io.Serializable;

import org.hibernate.validator.constraints.NotEmpty;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

public class ContactInfo implements Serializable {

    private static final long serialVersionUID = 4581832344361573326L;
    
    @NotNull
    @ApiModelProperty(allowableValues = "Fax, Email, Phone", required = true)
    private ContactInfoType type;
    
    @NotEmpty
    @ApiModelProperty(required = true)
    private String detail;
    
    @NotEmpty
    @ApiModelProperty(required = true)
    private boolean preferred;
    
    @ApiModelProperty(hidden = true)
    private String vendorNumber;

	public String getVendorNumber() {
		return vendorNumber;
	}

	public void setVendorNumber(String vendorNumber) {
		this.vendorNumber = vendorNumber;
	}

	public ContactInfoType getType() {
		return type;
	}

	public void setType(ContactInfoType type) {
		this.type = type;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public boolean isPreferred() {
		return preferred;
	}

	public void setPreferred(boolean preferred) {
		this.preferred = preferred;
	}
}
