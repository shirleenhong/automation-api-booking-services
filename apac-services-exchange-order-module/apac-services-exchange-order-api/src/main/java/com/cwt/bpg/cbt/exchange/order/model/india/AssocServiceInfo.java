package com.cwt.bpg.cbt.exchange.order.model.india;

import java.io.Serializable;
import java.math.BigDecimal;

import com.cwt.bpg.cbt.exchange.order.model.FormOfPayment;

import javax.validation.Valid;

public class AssocServiceInfo implements Serializable {

    private static final long serialVersionUID = -7551325200425808468L;

    private String details;

    private String productCode;

    private String vendorCode;

    @Valid
    private FormOfPayment formOfPayment;

    private BigDecimal nettCost;

    private BigDecimal commission;

    private BigDecimal discount;

    private BigDecimal sellingPrice;

    private BigDecimal vatGstAmount;

    private BigDecimal merchantFee;

    private String productDescription;

    private String cwtRefNoTk;

    private String vendorRefNoGsa;

    private String otherRelatedNoPo;
    
    private BigDecimal totalSellingPrice;
    
    private boolean clientExempt;
    
    private boolean noMerchantFee;

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getVendorCode() {
        return vendorCode;
    }

    public void setVendorCode(String vendorCode) {
        this.vendorCode = vendorCode;
    }

    public FormOfPayment getFormOfPayment() {
        return formOfPayment;
    }

    public void setFormOfPayment(FormOfPayment formOfPayment) {
        this.formOfPayment = formOfPayment;
    }

    public BigDecimal getNettCost() {
        return nettCost;
    }

    public void setNettCost(BigDecimal nettCost) {
        this.nettCost = nettCost;
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

    public BigDecimal getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(BigDecimal sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public BigDecimal getVatGstAmount() {
        return vatGstAmount;
    }

    public void setVatGstAmount(BigDecimal vatGstAmount) {
        this.vatGstAmount = vatGstAmount;
    }

    public BigDecimal getMerchantFee() {
        return merchantFee;
    }

    public void setMerchantFee(BigDecimal merchantFee) {
        this.merchantFee = merchantFee;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public String getCwtRefNoTk() {
        return cwtRefNoTk;
    }

    public void setCwtRefNoTk(String cwtRefNoTk) {
        this.cwtRefNoTk = cwtRefNoTk;
    }

    public String getVendorRefNoGsa() {
        return vendorRefNoGsa;
    }

    public void setVendorRefNoGsa(String vendorRefNoGsa) {
        this.vendorRefNoGsa = vendorRefNoGsa;
    }

    public String getOtherRelatedNoPo() {
        return otherRelatedNoPo;
    }

    public void setOtherRelatedNoPo(String otherRelatedNoPo) {
        this.otherRelatedNoPo = otherRelatedNoPo;
    }

	public BigDecimal getTotalSellingPrice() {
		return totalSellingPrice;
	}

	public void setTotalSellingPrice(BigDecimal totalSellingPrice) {
		this.totalSellingPrice = totalSellingPrice;
	}

	public boolean isClientExempt() {
		return clientExempt;
	}

	public void setClientExempt(boolean clientExempt) {
		this.clientExempt = clientExempt;
	}

	public boolean isNoMerchantFee() {
		return noMerchantFee;
	}

	public void setNoMerchantFee(boolean noMerchantFee) {
		this.noMerchantFee = noMerchantFee;
	}
}
