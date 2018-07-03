package com.cwt.bpg.cbt.exchange.order.model;

import java.io.Serializable;

public class EmailResponse implements Serializable {
    private static final long serialVersionUID = -1769736417984864802L;

    private boolean success;

    private boolean message;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public boolean isMessage() {
        return message;
    }

    public void setMessage(boolean message) {
        this.message = message;
    }
}
