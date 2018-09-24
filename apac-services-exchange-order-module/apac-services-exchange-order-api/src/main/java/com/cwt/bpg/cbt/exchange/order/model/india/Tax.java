package com.cwt.bpg.cbt.exchange.order.model.india;

import java.io.Serializable;
import java.math.BigDecimal;

public class Tax implements Serializable {
    private static final long serialVersionUID = -6692992451275632079L;

    private BigDecimal value;

    private String code;

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
