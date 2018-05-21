package com.cwt.bpg.cbt.tpromigration.mssqldb.model;

import java.util.List;

public class Vendor {

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

	private List<String> productCodes;

	private String raiseType;

	private String sortKey;

	private String vendorName;

	private String interfaceNumber;

	private String contactPerson;

	private String address;

	private String postalCode;

	private String state;

	private String vendorType;

	private Boolean requireEO;

	private Boolean requireAdvanced;

	private String HotelFee;

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getVendorNumber() {
		return this.vendorNumber;
	}

	public void setVendorNumber(String vendorNumber) {
		this.vendorNumber = vendorNumber;
	}

	public String getAddress1() {
		return this.address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getAddress2() {
		return this.address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getContactNo() {
		return this.contactNo;
	}

	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}

	public String getCountry() {
		return this.country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public Integer getCreditTerms() {
		return this.creditTerms;
	}

	public void setCreditTerms(Integer creditTerms) {
		this.creditTerms = creditTerms;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFaxNumber() {
		return this.faxNumber;
	}

	public void setFaxNumber(String faxNumber) {
		this.faxNumber = faxNumber;
	}

	public Boolean getMisc() {
		return this.misc;
	}

	public void setMisc(Boolean misc) {
		this.misc = misc;
	}

	public List<String> getProductCodes() {
		return this.productCodes;
	}

	public void setProductCodes(List<String> productCodes) {
		this.productCodes = productCodes;
	}

	public String getRaiseType() {
		return this.raiseType;
	}

	public void setRaiseType(String raiseType) {
		this.raiseType = raiseType;
	}

	public String getSortKey() {
		return this.sortKey;
	}

	public void setSortKey(String sortKey) {
		this.sortKey = sortKey;
	}

	public String getVendorName() {
		return this.vendorName;
	}

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}

	public String getInterfaceNumber() {
		return interfaceNumber;
	}

	public void setInterfaceNumber(String interfaceNumber) {
		this.interfaceNumber = interfaceNumber;
	}

	public String getContactPerson() {
		return contactPerson;
	}

	public void setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getVendorType() {
		return vendorType;
	}

	public void setVendorType(String vendorType) {
		this.vendorType = vendorType;
	}

	public Boolean getRequireEO() {
		return requireEO;
	}

	public void setRequireEO(Boolean requireEO) {
		this.requireEO = requireEO;
	}

	public Boolean getRequireAdvanced() {
		return requireAdvanced;
	}

	public void setRequireAdvanced(Boolean requireAdvanced) {
		this.requireAdvanced = requireAdvanced;
	}

	public String getHotelFee() {
		return HotelFee;
	}

	public void setHotelFee(String hotelFee) {
		HotelFee = hotelFee;
	}
}