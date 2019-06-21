package com.cwt.bpg.cbt.exchange.order.model;

import java.math.BigDecimal;
import javax.validation.constraints.NotNull;

import javax.validation.constraints.NotEmpty;

public class IndiaAirFeesInput extends FeesInput {

	private static final long serialVersionUID = 8399565390820971940L;

	@NotNull
	private BigDecimal baseFare;

	@NotNull
	private BigDecimal yqTax;
	private Double airlineCommissionPercent;
	private BigDecimal fee;
	private Double clientOverheadCommissionPercent;
	private BigDecimal airlineOverheadCommission;
	private Double airlineOverheadCommissionPercent;
	private boolean airlineOverheadCommissionByPercent;
	private Double merchantFeePercent;
	private BigDecimal othTax1;
	private BigDecimal othTax2;
	private BigDecimal othTax3;

	@NotEmpty
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

	public Double getAirlineCommissionPercent() {
		return airlineCommissionPercent;
	}

	public void setAirlineCommissionPercent(Double airlineCommissionPercent) {
		this.airlineCommissionPercent = airlineCommissionPercent;
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

	public BigDecimal getOthTax1() {
		return othTax1;
	}

	public void setOthTax1(BigDecimal othTax1) {
		this.othTax1 = othTax1;
	}

	public BigDecimal getOthTax2() {
		return othTax2;
	}

	public void setOthTax2(BigDecimal othTax2) {
		this.othTax2 = othTax2;
	}

	public BigDecimal getOthTax3() {
		return othTax3;
	}

	public void setOthTax3(BigDecimal othTax3) {
		this.othTax3 = othTax3;
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

	public BigDecimal getFee() {
		return fee;
	}

	public void setFee(BigDecimal fee) {
		this.fee = fee;
	}

	public boolean isAirlineOverheadCommissionByPercent() {
		return airlineOverheadCommissionByPercent;
	}

	public void setAirlineOverheadCommissionByPercent(boolean airlineOverheadCommissionByPercent) {
		this.airlineOverheadCommissionByPercent = airlineOverheadCommissionByPercent;
	}
}
