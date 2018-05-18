package com.cwt.bpg.cbt.tpromigration.mssqldb.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(value = { "clientId" })
public class BankVendor {
	
	private int clientId;
	private String vendorName;
	private Double percentage;
	private boolean standard;
	
	public int getClientId() {
		return clientId;
	}
	public void setClientId(int clientId) {
		this.clientId = clientId;
	}
	public String getVendorName() {
		return vendorName;
	}
	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}
	public Double getPercentage() {
		return percentage;
	}
	public void setPercentage(Double percentage) {
		this.percentage = percentage;
	}
	public boolean isStandard() {
		return standard;
	}
	public void setStandard(boolean standard) {
		this.standard = standard;
	}
	
	
}
