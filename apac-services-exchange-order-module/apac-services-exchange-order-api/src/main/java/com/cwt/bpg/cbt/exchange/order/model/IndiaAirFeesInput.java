package com.cwt.bpg.cbt.exchange.order.model;

import java.math.BigDecimal;

public class IndiaAirFeesInput extends FeesInput {

	private static final long serialVersionUID = 8399565390820971940L;

	private BigDecimal baseFare;
	private BigDecimal yqTax;
	private BigDecimal airlineCommission;
	private BigDecimal airlineOverheadCommission;
	private Double clientOverheadCommissionPercent;
	private Double airlineOverheadCommissionPercent;
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
	private int airSegmentCount;
	private Double discountPercent;

	private IndiaAirProductInput product;

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

	public Double getAirlineOverheadCommissionPercent() {
		return airlineOverheadCommissionPercent;
	}

	public void setAirlineOverheadCommissionPercent(Double airlineOverheadCommissionPercent) {
		this.airlineOverheadCommissionPercent = airlineOverheadCommissionPercent;
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

	public IndiaAirProductInput getProduct() {
		return product;
	}

	public void setProduct(IndiaAirProductInput product) {
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

	public int getAirSegmentCount() {
		return airSegmentCount;
	}

	public void setAirSegmentCount(int airSegmentCount) {
		this.airSegmentCount = airSegmentCount;
	}

	public BigDecimal getAirlineOverheadCommission() {
		return airlineOverheadCommission;
	}

	public void setAirlineOverheadCommission(BigDecimal airlineOverheadCommission) {
		this.airlineOverheadCommission = airlineOverheadCommission;
	}

	public Double getClientOverheadCommissionPercent() {
		return clientOverheadCommissionPercent;
	}

	public void setClientOverheadCommissionPercent(Double clientOverheadCommissionPercent) {
		this.clientOverheadCommissionPercent = clientOverheadCommissionPercent;
	}

	public Double getDiscountPercent() {
		return discountPercent;
	}

	public void setDiscountPercent(Double discountPercent) {
		this.discountPercent = discountPercent;
	}
}
