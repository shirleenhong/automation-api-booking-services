package com.cwt.bpg.cbt.exchange.order.model.india;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.validation.Valid;

import com.cwt.bpg.cbt.exchange.order.model.FormOfPayment;

public class NonAirCharges implements Serializable {

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
	
	private String description;
	
	private String cwtRefNo;
	
	private String vendorRefNo;
	
	private String otherRelatedNo;

    @Valid
    private FormOfPayment formOfPayment;

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

    public Boolean getGstAbsorb() {
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

    public Boolean getMerchantFeeAbsorb() {
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

    public Integer getValidityLength() {
        return validityLength;
    }

    public void setValidityLength(Integer validityLength) {
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCwtRefNo() {
		return cwtRefNo;
	}

	public void setCwtRefNo(String cwtRefNo) {
		this.cwtRefNo = cwtRefNo;
	}

	public String getVendorRefNo() {
		return vendorRefNo;
	}

	public void setVendorRefNo(String vendorRefNo) {
		this.vendorRefNo = vendorRefNo;
	}

	public String getOtherRelatedNo() {
		return otherRelatedNo;
	}

	public void setOtherRelatedNo(String otherRelatedNo) {
		this.otherRelatedNo = otherRelatedNo;
	}

    public FormOfPayment getFormOfPayment() {
        return formOfPayment;
    }

    public void setFormOfPayment(FormOfPayment formOfPayment) {
        this.formOfPayment = formOfPayment;
    }
}
