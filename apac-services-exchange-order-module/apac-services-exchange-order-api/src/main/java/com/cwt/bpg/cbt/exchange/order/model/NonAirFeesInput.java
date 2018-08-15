package com.cwt.bpg.cbt.exchange.order.model;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;

public class NonAirFeesInput extends FeesInput {

	private static final long serialVersionUID = 7271039286429340584L;

	@ApiModelProperty(required = true, allowableValues = "CX,CC,INV")
	@NotNull
    private FopTypes fopType;

	private BigDecimal sellingPrice;

	@ApiModelProperty(hidden = true, notes = "Not available in Power Express UI.")
	private boolean merchantFeeWaive;

	private boolean gstAbsorb;
	private boolean merchantFeeAbsorb;

	@ApiModelProperty(required = true)
	@NotNull
    private Double gstPercent;

	private BigDecimal nettCost;

	public NonAirFeesInput() {
        this.sellingPrice = BigDecimal.ZERO;
        this.gstPercent = 0d;
    }

	public boolean isMerchantFeeWaive() {
		return merchantFeeWaive;
	}

	public void setMerchantFeeWaive(boolean merchantFeeWaive) {
		this.merchantFeeWaive = merchantFeeWaive;
	}

	public boolean isGstAbsorb() {
		return gstAbsorb;
	}

	public void setGstAbsorb(boolean gstAbsorb) {
		this.gstAbsorb = gstAbsorb;
	}

	public BigDecimal getSellingPrice() {
		return sellingPrice;
	}

	public void setSellingPrice(BigDecimal sellingPrice) {
		this.sellingPrice = sellingPrice;
	}

	public boolean isMerchantFeeAbsorb() {
		return merchantFeeAbsorb;
	}

	public void setMerchantFeeAbsorb(boolean merchantFeeAbsorb) {
		this.merchantFeeAbsorb = merchantFeeAbsorb;
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

	public FopTypes getFopType() {
		return fopType;
	}

	public void setFopType(FopTypes fopType) {
		this.fopType = fopType;
	}
	
	
}

