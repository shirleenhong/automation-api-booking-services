package com.cwt.bpg.cbt.service.fee.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class PriceCalculationInput implements Serializable {

	private static final long serialVersionUID = 8501281316064680814L;
//	baseFare
//	totalTaxes
//	markupPercentage
//	markupAmount
//	commissionRebateAmount
//	commissionRebatePercentage
//	merchantFeeAmount
//	merchantFeePercentage
//		transactionFeeAmount (get lower amount)
//		transactionFeePercentage
//	obFee
//	fuelSurcharge
	
	private BigDecimal baseFare;
	
	private BigDecimal totalTaxes;
	
	private BigDecimal markupAmount;
	
	private Double markupPercentage;
	
	private BigDecimal commissionRebateAmount;
	
	private Double commissionRebatePercentage;
	
	private BigDecimal merchantFeeAmount;
	
	private Double merchantFeePercentage;

	private BigDecimal transactionFeeAmount;
	
	private Double transactionFeePercentage;
	
	private BigDecimal obFee;

	private BigDecimal fuelSurcharge;

	public BigDecimal getBaseFare() {
		return baseFare;
	}

	public void setBaseFare(BigDecimal baseFare) {
		this.baseFare = baseFare;
	}

	public BigDecimal getTotalTaxes() {
		return totalTaxes;
	}

	public void setTotalTaxes(BigDecimal totalTaxes) {
		this.totalTaxes = totalTaxes;
	}

	public BigDecimal getMarkupAmount() {
		return markupAmount;
	}

	public void setMarkupAmount(BigDecimal markupAmount) {
		this.markupAmount = markupAmount;
	}

	public Double getMarkupPercentage() {
		return markupPercentage;
	}

	public void setMarkupPercentage(Double markupPercentage) {
		this.markupPercentage = markupPercentage;
	}

	public BigDecimal getCommissionRebateAmount() {
		return commissionRebateAmount;
	}

	public void setCommissionRebateAmount(BigDecimal commissionRebateAmount) {
		this.commissionRebateAmount = commissionRebateAmount;
	}

	public Double getCommissionRebatePercentage() {
		return commissionRebatePercentage;
	}

	public void setCommissionRebatePercentage(Double commissionRebatePercentage) {
		this.commissionRebatePercentage = commissionRebatePercentage;
	}

	public BigDecimal getMerchantFeeAmount() {
		return merchantFeeAmount;
	}

	public void setMerchantFeeAmount(BigDecimal merchantFeeAmount) {
		this.merchantFeeAmount = merchantFeeAmount;
	}

	public Double getMerchantFeePercentage() {
		return merchantFeePercentage;
	}

	public void setMerchantFeePercentage(Double merchantFeePercentage) {
		this.merchantFeePercentage = merchantFeePercentage;
	}

	public BigDecimal getTransactionFeeAmount() {
		return transactionFeeAmount;
	}

	public void setTransactionFeeAmount(BigDecimal transactionFeeAmount) {
		this.transactionFeeAmount = transactionFeeAmount;
	}

	public Double getTransactionFeePercentage() {
		return transactionFeePercentage;
	}

	public void setTransactionFeePercentage(Double transactionFeePercentage) {
		this.transactionFeePercentage = transactionFeePercentage;
	}

	public BigDecimal getObFee() {
		return obFee;
	}

	public void setObFee(BigDecimal obFee) {
		this.obFee = obFee;
	}

	public BigDecimal getFuelSurcharge() {
		return fuelSurcharge;
	}

	public void setFuelSurcharge(BigDecimal fuelSurcharge) {
		this.fuelSurcharge = fuelSurcharge;
	}
}
