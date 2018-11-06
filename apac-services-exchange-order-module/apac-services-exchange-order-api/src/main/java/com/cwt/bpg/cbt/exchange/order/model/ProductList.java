package com.cwt.bpg.cbt.exchange.order.model;

import java.util.List;

public abstract class ProductList {
    private String countryCode;

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public abstract List getProducts();
}
