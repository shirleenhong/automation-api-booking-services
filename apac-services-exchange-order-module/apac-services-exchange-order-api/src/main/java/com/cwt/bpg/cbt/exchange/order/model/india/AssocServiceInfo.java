package com.cwt.bpg.cbt.exchange.order.model.india;

import java.io.Serializable;
import java.math.BigDecimal;

import com.cwt.bpg.cbt.exchange.order.model.FormOfPayment;

public class AssocServiceInfo implements Serializable {

    private static final long serialVersionUID = -7551325200425808468L;

    private String details;

    private String productCode;

    private String vendorCode;

    private FormOfPayment formOfPayment;

    private BigDecimal nettCost;

    private BigDecimal commission;

    private BigDecimal discount;

    private BigDecimal sellingPrice;

    private BigDecimal vatGstAmount;

    private BigDecimal merchantFeeAmount;

    private String productDescription;

    private String cwtRefNoTk;

    private String vendorRefNoGsa;

    private String otherRelatedNoPo;

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

    public BigDecimal getMerchantFeeAmount() {
        return merchantFeeAmount;
    }

    public void setMerchantFeeAmount(BigDecimal merchantFeeAmount) {
        this.merchantFeeAmount = merchantFeeAmount;
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
}
