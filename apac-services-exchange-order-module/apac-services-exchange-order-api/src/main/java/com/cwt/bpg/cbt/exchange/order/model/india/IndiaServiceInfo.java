package com.cwt.bpg.cbt.exchange.order.model.india;

import java.io.Serializable;
import java.math.BigDecimal;

import com.cwt.bpg.cbt.exchange.order.model.BaseServiceInfo;

public class IndiaServiceInfo extends BaseServiceInfo implements Serializable {

	private static final long serialVersionUID = -6797824920381793390L;

	private BigDecimal nettCost;

	private BigDecimal sellingPrice;

	private BigDecimal commission;

	private BigDecimal discount;

	private BigDecimal gstAmount;

	private boolean gstAbsorb;

	private BigDecimal merchantFeeAmount;

	private boolean merchantFeeAbsorb;

	private BigDecimal totalSellingPrice;

	private String country;

	private String type;

	private String entries;

	private int validityLength;

	private String validityType;

	private String processingType;

	private String classCabin;

    public BigDecimal getNettCost() {
        return nettCost;
    }

    public void setNettCost(BigDecimal nettCost) {
        this.nettCost = nettCost;
    }

    public BigDecimal getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(BigDecimal sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public BigDecimal getCommission() {
        return commission;
    }

    public void setCommission(BigDecimal commission) {
        this.commission = commission;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public BigDecimal getGstAmount() {
        return gstAmount;
    }

    public void setGstAmount(BigDecimal gstAmount) {
        this.gstAmount = gstAmount;
    }

    public boolean isGstAbsorb() {
        return gstAbsorb;
    }

    public void setGstAbsorb(boolean gstAbsorb) {
        this.gstAbsorb = gstAbsorb;
    }

    public BigDecimal getMerchantFeeAmount() {
        return merchantFeeAmount;
    }

    public void setMerchantFeeAmount(BigDecimal merchantFeeAmount) {
        this.merchantFeeAmount = merchantFeeAmount;
    }

    public boolean isMerchantFeeAbsorb() {
        return merchantFeeAbsorb;
    }

    public void setMerchantFeeAbsorb(boolean merchantFeeAbsorb) {
        this.merchantFeeAbsorb = merchantFeeAbsorb;
    }

    public BigDecimal getTotalSellingPrice() {
        return totalSellingPrice;
    }

    public void setTotalSellingPrice(BigDecimal totalSellingPrice) {
        this.totalSellingPrice = totalSellingPrice;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getEntries() {
        return entries;
    }

    public void setEntries(String entries) {
        this.entries = entries;
    }

    public int getValidityLength() {
        return validityLength;
    }

    public void setValidityLength(int validityLength) {
        this.validityLength = validityLength;
    }

    public String getValidityType() {
        return validityType;
    }

    public void setValidityType(String validityType) {
        this.validityType = validityType;
    }

    public String getProcessingType() {
        return processingType;
    }

    public void setProcessingType(String processingType) {
        this.processingType = processingType;
    }

    public String getClassCabin() {
        return classCabin;
    }

    public void setClassCabin(String classCabin) {
        this.classCabin = classCabin;
    }
}
