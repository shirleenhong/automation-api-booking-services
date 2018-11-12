package com.cwt.bpg.cbt.exchange.order.model;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;

public class Vendor extends BaseVendor implements Serializable {

	private static final long serialVersionUID = -6536616212682978836L;

	private String countryCode;

	private Integer creditTerms;

	private Boolean misc;

	@ApiModelProperty(value = "Missing raiseType is equivalent to null value")
	private String raiseType;

	private String sortKey;

	private String contactPerson;
	
	private Boolean merchantFeeAbsorb;

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public Integer getCreditTerms() {
		return creditTerms;
	}

	public void setCreditTerms(Integer creditTerms) {
		this.creditTerms = creditTerms;
	}

	public Boolean getMisc() {
		return misc;
	}

	public void setMisc(Boolean misc) {
		this.misc = misc;
	}

	public String getRaiseType() {
		return raiseType;
	}

	public void setRaiseType(String raiseType) {
		this.raiseType = raiseType;
	}

	public String getSortKey() {
		return sortKey;
	}

	public void setSortKey(String sortKey) {
		this.sortKey = sortKey;
	}

	public String getContactPerson() {
		return contactPerson;
	}

	public void setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
	}

	public Boolean getMerchantFeeAbsorb() {
		return merchantFeeAbsorb;
	}

	public void setMerchantFeeAbsorb(Boolean merchantFeeAbsorb) {
		this.merchantFeeAbsorb = merchantFeeAbsorb;
	}

}
