package com.cwt.bpg.cbt.exchange.order.model;

import java.math.BigDecimal;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

public class HkSgNonAirFeesInput extends NonAirFeesInput {

	private static final long serialVersionUID = 7271039286429340584L;

	@NotEmpty
	protected String fopType;
	protected BigDecimal sellingPrice;
	protected boolean isMerchantFeeWaive;
	private boolean isGstAbsorb;
	protected boolean isMerchantFeeAbsorb;
	@NotNull
	protected Double gstPercent;
	protected BigDecimal nettCost;

	public HkSgNonAirFeesInput() {
		this.sellingPrice = BigDecimal.ZERO;
		this.gstPercent = 0d;
	}

	public String getFopType() {
		return fopType;
	}

	public void setFopType(String fopType) {
		this.fopType = fopType;
	}

	public BigDecimal getSellingPrice() {
		return sellingPrice;
	}

	public void setSellingPrice(BigDecimal sellingPrice) {
		this.sellingPrice = sellingPrice;
	}

	public boolean isMerchantFeeWaive() {
		return isMerchantFeeWaive;
	}

	public void setMerchantFeeWaive(boolean merchantFeeWaive) {
		isMerchantFeeWaive = merchantFeeWaive;
	}

	public boolean isGstAbsorb() {
		return isGstAbsorb;
	}

	public void setGstAbsorb(boolean isGstAbsorb) {
		this.isGstAbsorb = isGstAbsorb;
	}

	public boolean isMerchantFeeAbsorb() {
		return isMerchantFeeAbsorb;
	}

	public void setMerchantFeeAbsorb(boolean merchantFeeAbsorb) {
		isMerchantFeeAbsorb = merchantFeeAbsorb;
	}

	public Double getGstPercent() {
		return gstPercent;
	}

	public void setGstPercent(Double gstPercent) {
		this.gstPercent = gstPercent;
	}

	public BigDecimal getNettCost() {
		return nettCost;
	}

	public void setNettCost(BigDecimal nettCost) {
		this.nettCost = nettCost;
	}
}
