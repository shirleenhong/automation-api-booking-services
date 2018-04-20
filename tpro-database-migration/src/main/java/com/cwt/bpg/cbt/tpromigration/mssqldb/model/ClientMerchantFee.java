package com.cwt.bpg.cbt.tpromigration.mssqldb.model;

public class ClientMerchantFee {
	
	private String clientName;
	
	private String productName;
	
	private String clientType;
	
	private Double merchantFeePct;
	
	private Boolean includeTransactionFee;
	
	public String getClientName() {
		return clientName;
	}
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
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

}
