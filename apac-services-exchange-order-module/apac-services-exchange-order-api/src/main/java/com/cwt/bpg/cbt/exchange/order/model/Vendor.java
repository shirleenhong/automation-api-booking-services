package com.cwt.bpg.cbt.exchange.order.model;

import java.io.Serializable;
import java.util.List;

import org.hibernate.validator.constraints.NotEmpty;

import io.swagger.annotations.ApiModelProperty;

public class Vendor implements Serializable {

	private static final long serialVersionUID = -6536616212682978836L;
	
	private String countryCode;

	@NotEmpty
	@ApiModelProperty(required = true)
	private String code;

	private String address1;

	private String address2;

	private String city;

	private String country;

	private Integer creditTerms;

	private Boolean misc;

	@ApiModelProperty(value="Missing raiseType is equivalent to null value")
	private String raiseType;

	private String sortKey;

	private String name;
	
	private String contactPerson;

	private String supportEmail;
	
	private List<ContactInfo> contactInfo;
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContactPerson() {
		return contactPerson;
	}

	public void setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
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

    public String getSupportEmail() {
        return supportEmail;
    }

    public void setSupportEmail(String supportEmail) {
        this.supportEmail = supportEmail;
    }

	public List<ContactInfo> getContactInfo() {
		return contactInfo;
	}

	public void setContactInfo(List<ContactInfo> contactInfo) {
		this.contactInfo = contactInfo;
	}
}
