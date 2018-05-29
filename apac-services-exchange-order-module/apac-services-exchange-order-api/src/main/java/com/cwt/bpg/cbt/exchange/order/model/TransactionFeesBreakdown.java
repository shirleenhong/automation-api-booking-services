package com.cwt.bpg.cbt.exchange.order.model;

import java.math.BigDecimal;

public class TransactionFeesBreakdown extends FeesBreakdown {

	private static final long serialVersionUID = 3942100881048994637L;

	private Double returnableOrPercent;
	private Double clientDiscountPercent;
	private Double vatPercent;
	private Double ot1Percent;
	private Double ot2Percent;
	private Double merchantFeePercent;
	private Double subMerchantFeePercent;
	private BigDecimal baseAmount;
	private BigDecimal totalIataCommission;
	private BigDecimal totalReturnableOr;
	private BigDecimal totalDiscount;
	private BigDecimal totalMarkup;
	private BigDecimal totalSellFare;
	private BigDecimal totalGst;
	private BigDecimal totalMerchantFee;
	private BigDecimal totalSellingFare;
	private BigDecimal totalTaxes;
	private BigDecimal merchantFeeOnTF;
	private BigDecimal totalCharge;

	public Double getReturnableOrPercent() {
		return returnableOrPercent;
	}

	public void setReturnableOrPercent(Double returnableOrPercent) {
		this.returnableOrPercent = returnableOrPercent;
	}

	public Double getClientDiscountPercent() {
		return clientDiscountPercent;
	}

	public void setClientDiscountPercent(Double clientDiscountPercent) {
		this.clientDiscountPercent = clientDiscountPercent;
	}

	public Double getVatPercent() {
		return vatPercent;
	}

	public void setVatPercent(Double vatPercent) {
		this.vatPercent = vatPercent;
	}

	public Double getOt1Percent() {
		return ot1Percent;
	}

	public void setOt1Percent(Double ot1Percent) {
		this.ot1Percent = ot1Percent;
	}

	public Double getOt2Percent() {
		return ot2Percent;
	}

	public void setOt2Percent(Double ot2Percent) {
		this.ot2Percent = ot2Percent;
	}

	public Double getMerchantFeePercent() {
		return merchantFeePercent;
	}

	public void setMerchantFeePercent(Double merchantFeePercent) {
		this.merchantFeePercent = merchantFeePercent;
	}

	public Double getSubMerchantFeePercent() {
		return subMerchantFeePercent;
	}

	public void setSubMerchantFeePercent(Double subMerchantFeePercent) {
		this.subMerchantFeePercent = subMerchantFeePercent;
	}

	public BigDecimal getBaseAmount() {
		return baseAmount;
	}

	public void setBaseAmount(BigDecimal baseAmount) {
		this.baseAmount = baseAmount;
	}

	public BigDecimal getTotalIataCommission() {
		return totalIataCommission;
	}

	public void setTotalIataCommission(BigDecimal totalIataCommission) {
		this.totalIataCommission = totalIataCommission;
	}

	public BigDecimal getTotalReturnableOr() {
		return totalReturnableOr;
	}

	public void setTotalReturnableOr(BigDecimal totalReturnableOr) {
		this.totalReturnableOr = totalReturnableOr;
	}

	public BigDecimal getTotalDiscount() {
		return totalDiscount;
	}

	public void setTotalDiscount(BigDecimal totalDiscount) {
		this.totalDiscount = totalDiscount;
	}

	public BigDecimal getTotalMarkup() {
		return totalMarkup;
	}

	public void setTotalMarkup(BigDecimal totalMarkup) {
		this.totalMarkup = totalMarkup;
	}

	public BigDecimal getTotalSellFare() {
		return totalSellFare;
	}

	public void setTotalSellFare(BigDecimal totalSellFare) {
		this.totalSellFare = totalSellFare;
	}

	public BigDecimal getTotalMerchantFee() {
		return totalMerchantFee;
	}

	public void setTotalMerchantFee(BigDecimal totalMerchantFee) {
		this.totalMerchantFee = totalMerchantFee;
	}

	public BigDecimal getTotalSellingFare() {
		return totalSellingFare;
	}

	public void setTotalSellingFare(BigDecimal totalSellingFare) {
		this.totalSellingFare = totalSellingFare;
	}

	public BigDecimal getTotalTaxes() {
		return totalTaxes;
	}

	public void setTotalTaxes(BigDecimal totalTaxes) {
		this.totalTaxes = totalTaxes;
	}

	public BigDecimal getMerchantFeeOnTF() {
		return merchantFeeOnTF;
	}

	public void setMerchantFeeOnTF(BigDecimal merchantFeeOnTF) {
		this.merchantFeeOnTF = merchantFeeOnTF;
	}

	public BigDecimal getTotalCharge() {
		return totalCharge;
	}

	public void setTotalCharge(BigDecimal totalCharge) {
		this.totalCharge = totalCharge;
	}

	public BigDecimal getTotalGst() {
		return totalGst;
	}

	public void setTotalGst(BigDecimal totalGst) {
		this.totalGst = totalGst;
	}

}
