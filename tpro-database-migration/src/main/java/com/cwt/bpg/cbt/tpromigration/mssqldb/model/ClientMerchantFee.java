package com.cwt.bpg.cbt.tpromigration.mssqldb.model;

public class ClientMerchantFee {
	
	private String clientName;
	
	private String profileName;
	
	private String clientType;
	
	private Double merchantFeePct;
	
	private Boolean includeTransactionFee;

	private String countryCode;

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public String getProfileName() {
		return profileName;
	}

	public void setProfileName(String profileName) {
		this.profileName = profileName;
	}

	public String getClientType() {
		return clientType;
	}

	public void setClientType(String clientType) {
		this.clientType = clientType;
	}

	public Double getMerchantFeePct() {
		return merchantFeePct;
	}

	public void setMerchantFeePct(Double merchantFeePct) {
		this.merchantFeePct = merchantFeePct;
	}

	public Boolean getIncludeTransactionFee() {
		return includeTransactionFee;
	}

	public void setIncludeTransactionFee(Boolean includeTransactionFee) {
		this.includeTransactionFee = includeTransactionFee;
	}
	
	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}
	
}
