package com.cwt.bpg.cbt.exchange.order.model;

import java.io.Serializable;

import org.mongodb.morphia.annotations.Property;

public class MerchantFee implements Serializable {

	private static final long serialVersionUID = -1922100420586710851L;

	private boolean includeTransactionFee;
	
	private Double merchantFeePct;
	
	private String clientType;
	
	private String clientName;
	
	/**
	 * proname
	 */
	private String productName;

	public boolean isIncludeTransactionFee() {
		return includeTransactionFee;
	}

	public void setIncludeTransactionFee(boolean includeTransactionFee) {
		this.includeTransactionFee = includeTransactionFee;
	}

	public String getClientType() {
		return clientType;
	}

	public void setClientType(String clientType) {
		this.clientType = clientType;
	}

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

	public Double getMerchantFeePct() {
		return merchantFeePct;
	}

	public void setMerchantFeePct(Double merchantFeePct) {
		this.merchantFeePct = merchantFeePct;
	}
}
