package com.cwt.bpg.cbt.exchange.order.model;

import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

public class VisaFeesInput extends FeesInput {

	private static final long serialVersionUID = -5761273312586606568L;

	@ApiModelProperty(required = true)
	@NotEmpty
	private String clientType;

	private boolean nettCostMerchantFeeChecked;
	private boolean cwtHandlingMerchantFeeChecked;
	private BigDecimal nettCost;
	@ApiModelProperty(required = true)
	@NotNull
	private BigDecimal cwtHandling;
	@ApiModelProperty(required = true)
	@NotNull
	private BigDecimal vendorHandling;

    public String getClientType()
    {
        return clientType;
    }

    public void setClientType(String clientType)
    {
        this.clientType = clientType;
    }

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
