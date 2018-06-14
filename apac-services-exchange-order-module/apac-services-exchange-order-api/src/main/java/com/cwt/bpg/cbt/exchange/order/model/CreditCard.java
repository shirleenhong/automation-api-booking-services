package com.cwt.bpg.cbt.exchange.order.model;

import java.io.Serializable;

public class CreditCard implements Serializable {

    private static final long serialVersionUID = 4581832344361573326L;
    private String ccType;
    private String ccNumber;
    private String expiryDate;

    public String getCcType() {
        return ccType;
    }

    public void setCcType(String ccType) {
        this.ccType = ccType;
    }

    public String getCcNumber() {
        return ccNumber;
    }

    public void setCcNumber(String ccNumber) {
        this.ccNumber = ccNumber;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }
}
