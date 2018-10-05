package com.cwt.bpg.cbt.exchange.order.model;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(value = { "cmpid" })
public class ClientPricing extends ClientFee implements Serializable {

	private static final long serialVersionUID = -1193230937085830329L;
	
	private String feeOption;
	private Integer cmpid;
	private Integer fieldId;
	private List<TransactionFee> transactionFees;

	public String getFeeOption() {
		return feeOption;
	}

	public void setFeeOption(String feeOption) {
		this.feeOption = feeOption;
	}

	public List<TransactionFee> getTransactionFees() {
		return transactionFees;
	}

	public void setTransactionFees(List<TransactionFee> transactionFees) {
		this.transactionFees = transactionFees;
	}

	public Integer getCmpid() {
		return cmpid;
	}

	public void setCmpid(Integer cmpid) {
		this.cmpid = cmpid;
	}

	public Integer getFieldId() {
		return fieldId;
	}

	public void setFieldId(Integer fieldId) {
		this.fieldId = fieldId;
	}

}
