package com.cwt.bpg.cbt.exchange.order.model;

import java.math.BigDecimal;

public class TransactionFeesInput extends FeesInput {

	private static final long serialVersionUID = 8399565390820971940L;

	private BigDecimal baseFare;
	private BigDecimal yqTax;
	private BigDecimal airlineCommission;
	private BigDecimal airlineOrCommission;
	private Double returnOrCommissionPercent;
	private Double airlineCommisionPercent;
	private Double merchantFeePercent;
	private BigDecimal fee;

	public BigDecimal getBaseFare() {
		return baseFare;
	}

	public void setBaseFare(BigDecimal baseFare) {
		this.baseFare = baseFare;
	}

	public BigDecimal getYqTax() {
		return yqTax;
	}

	public void setYqTax(BigDecimal yqTax) {
		this.yqTax = yqTax;
	}

	public BigDecimal getAirlineCommission() {
		return airlineCommission;
	}

	public void setAirlineCommission(BigDecimal airlineCommission) {
		this.airlineCommission = airlineCommission;
	}

	public BigDecimal getAirlineOrCommission() {
		return airlineOrCommission;
	}

	public void setAirlineOrCommission(BigDecimal airlineOrCommission) {
		this.airlineOrCommission = airlineOrCommission;
	}

	public Double getReturnOrCommissionPercent() {
		return returnOrCommissionPercent;
	}

	public void setReturnOrCommissionPercent(Double returnOrCommissionPercent) {
		this.returnOrCommissionPercent = returnOrCommissionPercent;
	}

	public Double getAirlineCommisionPercent() {
		return airlineCommisionPercent;
	}

	public void setAirlineCommisionPercent(Double airlineCommisionPercent) {
		this.airlineCommisionPercent = airlineCommisionPercent;
	}

	public Double getMerchantFeePercent() {
		return merchantFeePercent;
	}

	public void setMerchantFeePercent(Double merchantFeePercent) {
		this.merchantFeePercent = merchantFeePercent;
	}

	public BigDecimal getFee() {
		return fee;
	}

	public void setFee(BigDecimal fee) {
		this.fee = fee;
	}
}