package com.cwt.bpg.cbt.exchange.order.model;

import java.math.BigDecimal;

public class IndiaAirFeesBreakdown extends FeesBreakdown
{

	private static final long serialVersionUID = 3942100881048994637L;

	private Double overheadPercent;
	private Double gstPercent;
	private Double ot1Percent;
	private Double ot2Percent;
	private Double merchantFeePercent;
	private Double subMerchantFeePercent;
	private BigDecimal baseAmount;
	private BigDecimal totalAirlineCommission;
	private BigDecimal totalOverheadCommission;
	private BigDecimal totalDiscount;
	private BigDecimal totalMarkup;
	private BigDecimal totalSellFare;
	private BigDecimal totalGst;
	private BigDecimal totalMerchantFee;
	private BigDecimal totalSellingFare;
	private BigDecimal totalTaxes;
	private BigDecimal merchantFeeOnTf;
	private BigDecimal totalCharge;
	private BigDecimal fee;

	public Double getOverheadPercent() {
		return overheadPercent;
	}

	public void setOverheadPercent(Double percent) {
		this.overheadPercent = percent;
	}

	public Double getGstPercent() {
		return gstPercent;
	}

	public void setGstPercent(Double gstPercent) {
		this.gstPercent = gstPercent;
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

	public BigDecimal getTotalAirlineCommission() {
		return totalAirlineCommission;
	}

	public void setTotalAirlineCommission(BigDecimal totalCommission) {
		this.totalAirlineCommission = totalCommission;
	}

	public BigDecimal getTotalOverheadCommission() {
		return totalOverheadCommission;
	}

	public void setTotalOverheadCommission(BigDecimal commission) {
		this.totalOverheadCommission = commission;
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

	public BigDecimal getMerchantFeeOnTf() {
		return merchantFeeOnTf;
	}

	public void setMerchantFeeOnTf(BigDecimal merchantFeeOnTF) {
		this.merchantFeeOnTf = merchantFeeOnTF;
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

	public BigDecimal getFee() {
		return fee;
	}

	public void setFee(BigDecimal fee) {
		this.fee = fee;
	}
}
