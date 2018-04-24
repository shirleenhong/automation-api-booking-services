package com.cwt.bpg.cbt.service.fee.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class PriceBreakdown implements Serializable {
	
	private static final long serialVersionUID = -8034379136146857674L;

	private BigDecimal fopAmount;
	private BigDecimal merchantFeeAmount;
	private BigDecimal transactionFeeAmount;
	private BigDecimal markupAmount;
	private BigDecimal commissionRebateAmount;
	private BigDecimal airFareWithTaxAmount;
	private BigDecimal totalAmount;
	
	public PriceBreakdown() {
	}

	public PriceBreakdown(BigDecimal fopAmount, BigDecimal merchantFeeAmount, BigDecimal transactionFeeAmount, BigDecimal markupAmount,
			BigDecimal commissionRebateAmount, BigDecimal airFareWithTaxAmount, BigDecimal totalAmount) {
		this.fopAmount = fopAmount;
		this.merchantFeeAmount = merchantFeeAmount;
		this.transactionFeeAmount = transactionFeeAmount;
		this.markupAmount = markupAmount;
		this.commissionRebateAmount = commissionRebateAmount;
		this.airFareWithTaxAmount = airFareWithTaxAmount;
		this.totalAmount = totalAmount;
	}

	public BigDecimal getFopAmount() {
		return fopAmount;
	}

	public void setFopAmount(BigDecimal fopAmount) {
		this.fopAmount = fopAmount;
	}

	public BigDecimal getMerchantFeeAmount() {
		return merchantFeeAmount;
	}

	public void setMerchantFeeAmount(BigDecimal merchantFeeAmount) {
		this.merchantFeeAmount = merchantFeeAmount;
	}

	public BigDecimal getTransactionFeeAmount() {
		return transactionFeeAmount;
	}

	public void setTransactionFeeAmount(BigDecimal transactionFeeAmount) {
		this.transactionFeeAmount = transactionFeeAmount;
	}

	public BigDecimal getMarkupAmount() {
		return markupAmount;
	}

	public void setMarkupAmount(BigDecimal markupAmount) {
		this.markupAmount = markupAmount;
	}

	public BigDecimal getCommissionRebateAmount() {
		return commissionRebateAmount;
	}

	public void setCommissionRebateAmount(BigDecimal commissionRebateAmount) {
		this.commissionRebateAmount = commissionRebateAmount;
	}

	public BigDecimal getAirFareWithTaxAmount() {
		return airFareWithTaxAmount;
	}

	public void setAirFareWithTaxAmount(BigDecimal airFareWithTaxAmount) {
		this.airFareWithTaxAmount = airFareWithTaxAmount;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}
}
