package com.cwt.bpg.cbt.exchange.order.model;

import org.hibernate.validator.constraints.NotEmpty;
import org.mongodb.morphia.annotations.Entity;

import java.io.Serializable;

@Entity(value = "remarkList", noClassnameStored = true)
public class Remark implements Serializable {
    private static final long serialVersionUID = -756695804842165089L;

    @NotEmpty
    private String productType;

    @NotEmpty
    private String remarkType;

    @NotEmpty
    private String text;

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getRemarkType() {
        return remarkType;
    }

    public void setRemarkType(String remarkType) {
        this.remarkType = remarkType;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
