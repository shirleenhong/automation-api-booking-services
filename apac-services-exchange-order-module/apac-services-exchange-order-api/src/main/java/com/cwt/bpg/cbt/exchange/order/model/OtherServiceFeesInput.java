package com.cwt.bpg.cbt.exchange.order.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

public class OtherServiceFeesInput implements Serializable {
	
	private static final long serialVersionUID = -5237125856544162255L;
	
	public static final String INDIA = "IN";
	public static final String HONG_KONG = "HK";
	
	public OtherServiceFeesInput() {
		this.sellingPrice = BigDecimal.ZERO;
	}
	
	@NotEmpty
	private String fopType; 
	private BigDecimal sellingPrice;
	private boolean isGstAbsorb;
	private boolean isMerchantFeeAbsorb;
	private boolean isMerchantFeeWaive;	
	@NotEmpty
	private String marketCode;
	@NotEmpty
	private String currencyCode;
	private String countryCode;
	@NotEmpty
	private String clientType;
	@NotEmpty
	private String productName;
	@NotNull
	private Double gstPercent;
	private BigDecimal nettCost;

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
	public boolean isGstAbsorb() {
		return isGstAbsorb;
	}
	public void setGstAbsorb(boolean isGstAbsorb) {
		this.isGstAbsorb = isGstAbsorb;
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
	public String getMarketCode() {
		return marketCode;
	}
	public void setMarketCode(String marketCode) {
		this.marketCode = marketCode;
	}
	public String getCurrencyCode() {
		return currencyCode;
	}
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
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
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
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
