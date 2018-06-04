package com.cwt.bpg.cbt.tpromigration.mssqldb.model;

import java.util.List;

public class Vendor extends com.cwt.bpg.cbt.exchange.order.model.Vendor {

	private static final long serialVersionUID = -8841743104520862214L;

	private List<String> productCodes;

	private String interfaceNumber;

	private String contactPerson;

	private String postalCode;

	private String state;

	private String vendorType;

	private Boolean requireEo;

	private String hotelFee;
	
	private Boolean requireAdvanced;

	public List<String> getProductCodes() {
		return productCodes;
	}

	public void setProductCodes(List<String> productCodes) {
		this.productCodes = productCodes;
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

	public Boolean getRequireEo() {
		return requireEo;
	}

	public void setRequireEo(Boolean requireEo) {
		this.requireEo = requireEo;
	}

	public String getHotelFee() {
		return hotelFee;
	}

	public void setHotelFee(String hotelFee) {
		this.hotelFee = hotelFee;
	}

	public Boolean getRequireAdvanced() {
		return requireAdvanced;
	}

	public void setRequireAdvanced(Boolean requireAdvanced) {
		this.requireAdvanced = requireAdvanced;
	}

}
