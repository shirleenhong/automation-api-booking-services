package com.cwt.bpg.cbt.tpromigration.mssqldb.model;

import java.math.BigDecimal;

public class InClientTransactionFee {
	
	private String clientAccountNumber;
	private BigDecimal intOfflineAmount;
	private BigDecimal domOfflineAmount;
	private BigDecimal lccOfflineAmount;

	public String getClientAccountNumber() {
		return clientAccountNumber;
	}

	public void setClientAccountNumber(String clientAccountNumber) {
		this.clientAccountNumber = clientAccountNumber;
	}

	public BigDecimal getIntOfflineAmount() {
		return intOfflineAmount;
	}

	public void setIntOfflineAmount(BigDecimal intOfflineAmount) {
		this.intOfflineAmount = intOfflineAmount;
	}

	public BigDecimal getDomOfflineAmount() {
		return domOfflineAmount;
	}

	public void setDomOfflineAmount(BigDecimal domOfflineAmount) {
		this.domOfflineAmount = domOfflineAmount;
	}

	public BigDecimal getLccOfflineAmount() {
		return lccOfflineAmount;
	}

	public void setLccOfflineAmount(BigDecimal lccOfflineAmount) {
		this.lccOfflineAmount = lccOfflineAmount;
	}
}
