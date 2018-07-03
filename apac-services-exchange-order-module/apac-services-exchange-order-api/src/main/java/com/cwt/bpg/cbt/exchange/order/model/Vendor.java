package com.cwt.bpg.cbt.exchange.order.model;

import java.io.Serializable;

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

	private String contactNo;

	private String country;

	private Integer creditTerms;

	private String email;

	private String faxNumber;

	private Boolean misc;

	@ApiModelProperty(value="Missing raiseType is equivalent to null value")
	private String raiseType;

	private String sortKey;

	private String name;
	
	private String contactPerson;
	
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

	public String getContactNo() {
		return contactNo;
	}

	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFaxNumber() {
		return faxNumber;
	}

	public void setFaxNumber(String faxNumber) {
		this.faxNumber = faxNumber;
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
}
