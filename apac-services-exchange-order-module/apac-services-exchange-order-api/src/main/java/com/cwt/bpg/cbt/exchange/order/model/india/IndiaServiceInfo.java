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

	private BigDecimal gst;

	private Boolean gstAbsorb;

	private BigDecimal merchantFee;

	private Boolean merchantFeeAbsorb;

	private BigDecimal totalSellingPrice;

	private String country;

	private String type;

	private String entries;

	private Integer validityLength;

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

    public BigDecimal getGst() {
        return gst;
    }

    public void setGst(BigDecimal gst) {
        this.gst = gst;
    }

    public Boolean isGstAbsorb() {
        return gstAbsorb;
    }

    public void setGstAbsorb(Boolean gstAbsorb) {
        this.gstAbsorb = gstAbsorb;
    }

    public BigDecimal getMerchantFee() {
        return merchantFee;
    }

    public void setMerchantFee(BigDecimal merchantFee) {
        this.merchantFee = merchantFee;
    }

    public Boolean isMerchantFeeAbsorb() {
        return merchantFeeAbsorb;
    }

    public void setMerchantFeeAbsorb(Boolean merchantFeeAbsorb) {
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
