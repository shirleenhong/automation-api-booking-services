package com.cwt.bpg.cbt.exchange.order.model;

public enum Timezone
{
    PT("PT"),
    ET("ET");

    private final String code;

    Timezone(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
