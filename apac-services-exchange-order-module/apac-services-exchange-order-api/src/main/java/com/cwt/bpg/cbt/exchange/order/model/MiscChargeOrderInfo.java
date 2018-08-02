package com.cwt.bpg.cbt.exchange.order.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class MiscChargeOrderInfo implements Serializable {

	private static final long serialVersionUID = -9147782712919219597L;

	private String recordLocator;

	private String serviceType;

	private String locationOfIssuance;

	private String contact;

	private String fop;

	private BigDecimal equivalentAmtPaid;

	private String headlineCurrency;

	private BigDecimal taxes;

	private double rateOfExchange;

	private String exchangeIssue;

	private String inConjunctionWith;

	private String originalFOP;

	private String originalPlaceOfIssue;

	public String getRecordLocator() {
		return recordLocator;
	}

	public void setRecordLocator(String recordLocator) {
		this.recordLocator = recordLocator;
	}

	public String getServiceType() {
		return serviceType;
	}

	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}

	public String getLocationOfIssuance() {
		return locationOfIssuance;
	}

	public void setLocationOfIssuance(String locationOfIssuance) {
		this.locationOfIssuance = locationOfIssuance;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getFop() {
		return fop;
	}

	public void setFop(String fOP) {
		fop = fOP;
	}

	public BigDecimal getEquivalentAmtPaid() {
		return equivalentAmtPaid;
	}

	public void setEquivalentAmtPaid(BigDecimal equivalentAmtPaid) {
		this.equivalentAmtPaid = equivalentAmtPaid;
	}

	public String getHeadlineCurrency() {
		return headlineCurrency;
	}

	public void setHeadlineCurrency(String headlineCurrency) {
		this.headlineCurrency = headlineCurrency;
	}

	public BigDecimal getTaxes() {
		return taxes;
	}

	public void setTaxes(BigDecimal taxes) {
		this.taxes = taxes;
	}

	public double getRateOfExchange() {
		return rateOfExchange;
	}

	public void setRateOfExchange(double rateOfExchange) {
		this.rateOfExchange = rateOfExchange;
	}

	public String getExchangeIssue() {
		return exchangeIssue;
	}

	public void setExchangeIssue(String exchangeIssue) {
		this.exchangeIssue = exchangeIssue;
	}

	public String getInConjunctionWith() {
		return inConjunctionWith;
	}

	public void setInConjunctionWith(String inConjunctionWith) {
		this.inConjunctionWith = inConjunctionWith;
	}

	public String getOriginalFOP() {
		return originalFOP;
	}

	public void setOriginalFOP(String originalFOP) {
		this.originalFOP = originalFOP;
	}

	public String getOriginalPlaceOfIssue() {
		return originalPlaceOfIssue;
	}

	public void setOriginalPlaceOfIssue(String originalPlaceOfIssue) {
		this.originalPlaceOfIssue = originalPlaceOfIssue;
	}

}
