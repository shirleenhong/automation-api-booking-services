package com.cwt.bpg.cbt.exchange.order.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

public class OtherServiceFeesInput implements Serializable {
	
	private static final long serialVersionUID = -5237125856544162255L;
	
	@NotEmpty
	protected String fopType; 
	protected BigDecimal sellingPrice;
	protected boolean isMerchantFeeAbsorb;
	protected boolean isMerchantFeeWaive;	
	private String countryCode;
	@NotEmpty
	protected String clientType;
	@NotEmpty
	protected String profileName;
	@NotNull
	protected Double gstPercent;
	protected BigDecimal nettCost;

	public OtherServiceFeesInput() {
		this.sellingPrice = BigDecimal.ZERO;
		this.gstPercent = new Double(0);
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
	public boolean isMerchantFeeAbsorb() {
		return isMerchantFeeAbsorb;
	}
	public void setMerchantFeeAbsorb(boolean isMerchantFeeAbsorb) {
		this.isMerchantFeeAbsorb = isMerchantFeeAbsorb;
	}
	public boolean isMerchantFeeWaive() {
		return isMerchantFeeWaive;
	}
	public void setMerchantFeeWaive(boolean isMerchantFeeWaive) {
		this.isMerchantFeeWaive = isMerchantFeeWaive;
	}	
	public String getCountryCode() {
		return countryCode;
	}
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}
	public String getClientType() {
		return clientType;
	}
	public void setClientType(String clientType) {
		this.clientType = clientType;
	}
	public String getProfileName() {
		return profileName;
	}
	public void setProfileName(String profileName) {
		this.profileName = profileName;
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
