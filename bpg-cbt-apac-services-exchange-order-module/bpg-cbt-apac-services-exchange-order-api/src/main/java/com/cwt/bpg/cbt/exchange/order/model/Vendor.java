package com.cwt.bpg.cbt.exchange.order.model;

import java.io.Serializable;

public class Vendor implements Serializable {

	private static final long serialVersionUID = -6536616212682978836L;
	
	public static final String COLLECTION = "_vendors";
	
	private String countryCode;
	
	private String vendorNumber;

	private String address1;

	private String address2;

	private String city;

	private String contactNo;

	private String country;

	private Integer creditTerms;

	private String email;

	private String faxNumber;

	private Boolean misc;

//	private List<String> productCodes;

	private String raiseType;

	private String sortKey;

	private String vendorName;

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getVendorNumber() {
		return vendorNumber;
	}

	public void setVendorNumber(String vendorNumber) {
		this.vendorNumber = vendorNumber;
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

//	public List<String> getProductCodes() {
//		return productCodes;
//	}
//
//	public void setProductCodes(List<String> productCodes) {
//		this.productCodes = productCodes;
//	}

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

	public String getVendorName() {
		return vendorName;
	}

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}
	
	

}
