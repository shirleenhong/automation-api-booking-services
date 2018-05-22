package com.cwt.bpg.cbt.exchange.order.model;

import java.io.Serializable;
import java.util.List;

public class ClientPricing implements Serializable {
	
	private static final long serialVersionUID = -1193230937085830329L;
	
	private String tripType;
	private String feeOption;
	private Integer group;
	private Integer cmpid;
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

	public Integer getGroup() {
		return group;
	}

	public void setGroup(Integer group) {
		this.group = group;
	}

	public Integer getCmpid() {
		return cmpid;
	}

	public void setCmpid(Integer cmpid) {
		this.cmpid = cmpid;
	}

}
