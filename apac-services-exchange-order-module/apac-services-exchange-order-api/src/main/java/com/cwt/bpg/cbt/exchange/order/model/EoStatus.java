package com.cwt.bpg.cbt.exchange.order.model;

import com.fasterxml.jackson.annotation.JsonValue;

public enum EoStatus {
    NEW("New"),
    PENDING("Pending"),
    COMPLETED("Completed"),
    CANCELLED("Cancelled");

    @JsonValue
    private final String value;

    EoStatus(String value) {
        this.value = value;
    }

    public static EoStatus find(final String value) {
        for (final EoStatus status : EoStatus.values()) {
            if (status.value.equals(value)) {
                return status;
            }
        }
        return null;
    }
}
