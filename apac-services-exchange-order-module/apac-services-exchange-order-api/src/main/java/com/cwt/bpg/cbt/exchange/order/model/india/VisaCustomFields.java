package com.cwt.bpg.cbt.exchange.order.model.india;

import java.io.Serializable;

public class VisaCustomFields implements Serializable {

	private static final long serialVersionUID = 8583039322655992745L;

	private String demandDraftRequired;

	private String demandDraftNumber;

	private String validity;

	public String getDemandDraftRequired() {
		return demandDraftRequired;
	}

	public void setDemandDraftRequired(String demandDraftRequired) {
		this.demandDraftRequired = demandDraftRequired;
	}

	public String getDemandDraftNumber() {
		return demandDraftNumber;
	}

	public void setDemandDraftNumber(String demandDraftNumber) {
		this.demandDraftNumber = demandDraftNumber;
	}

	public String getValidity() {
		return validity;
	}

	public void setValidity(String validity) {
		this.validity = validity;
	}
}
