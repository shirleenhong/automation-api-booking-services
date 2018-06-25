package com.cwt.bpg.cbt.exchange.order.model;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;

public class VisaFeesInput extends FeesInput {

	private static final long serialVersionUID = -5761273312586606568L;

	@ApiModelProperty(required = true)
	@NotNull
	private BigDecimal cwtHandling;

	@ApiModelProperty(required = true)
	@NotNull
	private BigDecimal vendorHandling;

	private boolean nettCostMerchantFeeChecked;
	private boolean cwtHandlingMerchantFeeChecked;
	private BigDecimal nettCost;

    public BigDecimal getNettCost() {
		return nettCost;
	}

	public void setNettCost(BigDecimal nettCost) {
		this.nettCost = nettCost;
	}

	public boolean isNettCostMerchantFeeChecked() {
		return nettCostMerchantFeeChecked;
	}

	public void setNettCostMerchantFeeChecked(boolean nettCostMerchantFeeChecked) {
		this.nettCostMerchantFeeChecked = nettCostMerchantFeeChecked;
	}

	public boolean isCwtHandlingMerchantFeeChecked() {
		return cwtHandlingMerchantFeeChecked;
	}

	public void setCwtHandlingMerchantFeeChecked(boolean cwtHandlingMerchantFeeChecked) {
		this.cwtHandlingMerchantFeeChecked = cwtHandlingMerchantFeeChecked;
	}

	public BigDecimal getCwtHandling() {
		return cwtHandling;
	}

	public void setCwtHandling(BigDecimal cwtHandling) {
		this.cwtHandling = cwtHandling;
	}

	public BigDecimal getVendorHandling() {
		return vendorHandling;
	}

	public void setVendorHandling(BigDecimal vendorHandling) {
		this.vendorHandling = vendorHandling;
	}
}
