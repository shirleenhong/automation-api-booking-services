package com.cwt.bpg.cbt.exchange.order.model;

import java.math.BigDecimal;

public class MiscChargeOrderInfo {

	private String recordLocator;
	private String typeOfService;
	private String locationOfIssuance;
	private String contact;
	private String FOP;
	private BigDecimal equivalentAmtPaid;
	private String headlineCurrency;
	private BigDecimal Taxes;
	private double rateofExchange;
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

	public String getTypeOfService() {
		return typeOfService;
	}

	public void setTypeOfService(String typeOfService) {
		this.typeOfService = typeOfService;
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

	public String getFOP() {
		return FOP;
	}

	public void setFOP(String fOP) {
		FOP = fOP;
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
		return Taxes;
	}

	public void setTaxes(BigDecimal taxes) {
		Taxes = taxes;
	}

	public double getRateofExchange() {
		return rateofExchange;
	}

	public void setRateofExchange(double rateofExchange) {
		this.rateofExchange = rateofExchange;
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
