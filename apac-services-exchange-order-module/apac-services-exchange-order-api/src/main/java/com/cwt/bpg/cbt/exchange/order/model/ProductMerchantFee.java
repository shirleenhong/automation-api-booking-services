package com.cwt.bpg.cbt.exchange.order.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(value = { "clientId" })
public class ProductMerchantFee implements Serializable {

	private static final long serialVersionUID = -214591267757226656L;
	
	private int clientId;
	private int productCode;
	private boolean subjectToMf;
	private boolean standard;

	public int getClientId() {
		return clientId;
	}

	public void setClientId(int clientId) {
		this.clientId = clientId;
	}

	public boolean isSubjectToMf() {
		return subjectToMf;
	}

	public void setSubjectToMf(boolean subjectToMf) {
		this.subjectToMf = subjectToMf;
	}

	public boolean isStandard() {
		return standard;
	}

	public void setStandard(boolean standard) {
		this.standard = standard;
	}

	public int getProductCode() {
		return productCode;
	}

	public void setProductCode(int productCode) {
		this.productCode = productCode;
	}

}
