package com.cwt.bpg.cbt.exchange.order.model;

import java.io.Serializable;
import java.math.BigDecimal;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

public class VisaFeesInput implements Serializable {

    private static final long serialVersionUID = -5761273312586606568L;
    @NotNull
    private String countryCode;
    @NotEmpty
    private String clientType;
    @NotEmpty
    private String profileName;
    private boolean nettCostMerchantFeeChecked;
	private boolean cwtHandlingMerchantFeeChecked;
	private BigDecimal nettCost;
    @NotNull
	private BigDecimal cwtHandling;
    @NotNull
	private BigDecimal vendorHandling;

    public String getCountryCode()
    {
        return countryCode;
    }

    public void setCountryCode(String countryCode)
    {
        this.countryCode = countryCode;
    }

    public String getClientType()
    {
        return clientType;
    }

    public void setClientType(String clientType)
    {
        this.clientType = clientType;
    }

    public String getProfileName()
    {
        return profileName;
    }

    public void setProfileName(String profileName)
    {
        this.profileName = profileName;
    }

    public BigDecimal getNettCost()
    {
        return nettCost;
    }

    public void setNettCost(BigDecimal nettCost)
    {
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
