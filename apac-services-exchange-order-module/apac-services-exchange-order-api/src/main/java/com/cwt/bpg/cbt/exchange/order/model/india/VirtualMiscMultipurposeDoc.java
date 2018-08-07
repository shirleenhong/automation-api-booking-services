package com.cwt.bpg.cbt.exchange.order.model.india;

import java.io.Serializable;
import java.math.BigDecimal;

import com.cwt.bpg.cbt.exchange.order.model.FormOfPayment;

import javax.validation.Valid;

public class VirtualMiscMultipurposeDoc implements Serializable {

	private static final long serialVersionUID = 4559501238956808793L;
	private String recordLocator;

	private BigDecimal fare;

	private BigDecimal taxes;

	private String reasonForIssue;

	private String headlineCurrency;

	private BigDecimal equivalentAmountPaid;

	@Valid
	private FormOfPayment formOfPayment;

	private String issueInExchangeFor;

	private String inConjuctionWith;

	private String origPlaceOfIssue;

	private String remark;

	public String getRecordLocator() {
		return recordLocator;
	}

	public void setRecordLocator(String recordLocator) {
		this.recordLocator = recordLocator;
	}

	public BigDecimal getFare() {
		return fare;
	}

	public void setFare(BigDecimal fare) {
		this.fare = fare;
	}

	public BigDecimal getTaxes() {
		return taxes;
	}

	public void setTaxes(BigDecimal taxes) {
		this.taxes = taxes;
	}

	public String getReasonForIssue() {
		return reasonForIssue;
	}

	public void setReasonForIssue(String reasonForIssue) {
		this.reasonForIssue = reasonForIssue;
	}

	public String getHeadlineCurrency() {
		return headlineCurrency;
	}

	public void setHeadlineCurrency(String headlineCurrency) {
		this.headlineCurrency = headlineCurrency;
	}

	public BigDecimal getEquivalentAmountPaid() {
		return equivalentAmountPaid;
	}

	public void setEquivalentAmountPaid(BigDecimal equivalentAmountPaid) {
		this.equivalentAmountPaid = equivalentAmountPaid;
	}

	public FormOfPayment getFormOfPayment() {
		return formOfPayment;
	}

	public void setFormOfPayment(FormOfPayment formOfPayment) {
		this.formOfPayment = formOfPayment;
	}

	public String getIssueInExchangeFor() {
		return issueInExchangeFor;
	}

	public void setIssueInExchangeFor(String issueInExchangeFor) {
		this.issueInExchangeFor = issueInExchangeFor;
	}

	public String getInConjuctionWith() {
		return inConjuctionWith;
	}

	public void setInConjuctionWith(String inConjuctionWith) {
		this.inConjuctionWith = inConjuctionWith;
	}

	public String getOrigPlaceOfIssue() {
		return origPlaceOfIssue;
	}

	public void setOrigPlaceOfIssue(String origPlaceOfIssue) {
		this.origPlaceOfIssue = origPlaceOfIssue;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
