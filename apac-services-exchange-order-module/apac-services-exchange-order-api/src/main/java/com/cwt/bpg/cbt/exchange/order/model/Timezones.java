package com.cwt.bpg.cbt.exchange.order.model;

public enum Timezones
{
    PT("PT"),
    ET("ET");

    private final String code;

    Timezones(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
