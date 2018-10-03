package com.cwt.bpg.cbt.exchange.order.model;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(value = { "cmpid" })
public class ClientPricing implements Serializable {

	private static final long serialVersionUID = -1193230937085830329L;
	
	private String tripType;
	private String feeOption;
	private Integer value;
	private Integer cmpid;
	private Integer fieldId;
	private String feeName;
	private List<TransactionFee> transactionFees;

	public String getTripType() {
		return tripType;
	}

	public void setTripType(String tripType) {
		this.tripType = tripType;
	}

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

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
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

	public String getFeeName() {
		return feeName;
	}

	public void setFeeName(String feeName) {
		this.feeName = feeName;
	}
}
