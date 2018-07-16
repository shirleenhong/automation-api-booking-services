package com.cwt.bpg.cbt.exchange.order.model;

import java.io.Serializable;

import org.hibernate.validator.constraints.NotEmpty;

import io.swagger.annotations.ApiModelProperty;

public class ContactInfo implements Serializable {

    private static final long serialVersionUID = 4581832344361573326L;
    
    @NotEmpty
    @ApiModelProperty(allowableValues = "Fax, Email, Phone", required = true)
    private String contactType;
    
    @NotEmpty
    @ApiModelProperty(required = true)
    private String contactDetails;
    
    @NotEmpty
    @ApiModelProperty(required = true)
    private Boolean preferred;
    
    @ApiModelProperty(hidden = true)
    private String vendorNumber;

	public String getVendorNumber() {
		return vendorNumber;
	}

	public void setVendorNumber(String vendorNumber) {
		this.vendorNumber = vendorNumber;
	}

	public String getContactType() {
		return contactType;
	}

	public void setContactType(String contactType) {
		this.contactType = contactType;
	}

	public String getContactDetails() {
		return contactDetails;
	}

	public void setContactDetails(String contactDetails) {
		this.contactDetails = contactDetails;
	}

	public Boolean getPreferred() {
		return preferred;
	}

	public void setPreferred(Boolean preferred) {
		this.preferred = preferred;
	}
    

    
}
