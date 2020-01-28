package com.cwt.bpg.cbt.exchange.order.model;

import java.io.Serializable;

public class MerchantFeeCreditCardVendor implements Serializable
{
    private String vendorCode;
    private Double percentage;

    public String getVendorCode() {
        return vendorCode;
    }

    public void setVendorCode(String vendorCode) {
        this.vendorCode = vendorCode;
    }

    public Double getPercentage() {
        return percentage;
    }

    public void setPercentage(Double percentage) {
        this.percentage = percentage;
    }

}
