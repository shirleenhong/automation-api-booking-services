package com.cwt.bpg.cbt.tpromigration.mssqldb.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(value = { "clientId" })
public class Bank {

	private int clientId;
	private int bankId;
	private String bankName;
	private String ccNumberPrefix;
	private Double percentage;
	private boolean standard;

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getCcNumberPrefix() {
		return ccNumberPrefix;
	}

	public void setCcNumberPrefix(String ccNumberPrefix) {
		this.ccNumberPrefix = ccNumberPrefix;
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

	public int getBankId() {
		return bankId;
	}

	public void setBankId(int bankId) {
		this.bankId = bankId;
	}

	public int getClientId() {
		return clientId;
	}

	public void setClientId(int clientId) {
		this.clientId = clientId;
	}

}
