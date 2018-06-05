package com.cwt.bpg.cbt.exchange.order.model;

import java.math.BigDecimal;

public class InAirFeesInput extends AirFeesInput {

	private static final long serialVersionUID = 8399565390820971940L;

	private BigDecimal baseFare;
	private BigDecimal yqTax;
	private BigDecimal airlineCommission;
	private BigDecimal airlineOrCommission;
	private Double returnOrCommissionPercent;
	private Double airlineCommissionPercent;
	private Double merchantFeePercent;
	private BigDecimal tax1;
	private BigDecimal tax2;
	private String tripType;
	private String platCarrier;
	private boolean gstEnabled;
	private boolean commissionEnabled;
	private boolean overheadCommissionEnabled;
	private boolean markupEnabled;
	private boolean discountEnabled;
	
	private InProduct product;
	
	private boolean feeOverride;
	
	private String cityCode;
	
	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public boolean isFeeOverride() {
		return feeOverride;
	}

	public void setFeeOverride(boolean feeOverride) {
		this.feeOverride = feeOverride;
	}

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

	public Double getAirlineCommissionPercent() {
		return airlineCommissionPercent;
	}

	public void setAirlineCommissionPercent(Double airlineCommissionPercent) {
		this.airlineCommissionPercent = airlineCommissionPercent;
	}

	public Double getMerchantFeePercent() {
		return merchantFeePercent;
	}

	public void setMerchantFeePercent(Double merchantFeePercent) {
		this.merchantFeePercent = merchantFeePercent;
	}

	public String getTripType() {
		return tripType;
	}

	public void setTripType(String tripType) {
		this.tripType = tripType;
	}

	public String getPlatCarrier() {
		return platCarrier;
	}

	public void setPlatCarrier(String platCarrier) {
		this.platCarrier = platCarrier;
	}

	public boolean isGstEnabled() {
		return gstEnabled;
	}

	public void setGstEnabled(boolean gstEnabled) {
		this.gstEnabled = gstEnabled;
	}

	public InProduct getProduct() {
		return product;
	}

	public void setProduct(InProduct product) {
		this.product = product;
	}

	public boolean isCommissionEnabled() {
		return commissionEnabled;
	}

	public void setCommissionEnabled(boolean commissionEnabled) {
		this.commissionEnabled = commissionEnabled;
	}

	public boolean isOverheadCommissionEnabled() {
		return overheadCommissionEnabled;
	}

	public void setOverheadCommissionEnabled(boolean commissionEnabled) {
		this.overheadCommissionEnabled = commissionEnabled;
	}

	public boolean isMarkupEnabled() {
		return markupEnabled;
	}

	public void setMarkupEnabled(boolean markupEnabled) {
		this.markupEnabled = markupEnabled;
	}

	public boolean isDiscountEnabled() {
		return discountEnabled;
	}

	public void setDiscountEnabled(boolean discountEnabled) {
		this.discountEnabled = discountEnabled;
	}

	public BigDecimal getTax1() {
		return tax1;
	}

	public void setTax1(BigDecimal tax1) {
		this.tax1 = tax1;
	}

	public BigDecimal getTax2() {
		return tax2;
	}

	public void setTax2(BigDecimal tax2) {
		this.tax2 = tax2;
	}
	
}
