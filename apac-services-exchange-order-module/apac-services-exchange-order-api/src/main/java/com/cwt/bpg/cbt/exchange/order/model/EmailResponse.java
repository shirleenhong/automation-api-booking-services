package com.cwt.bpg.cbt.exchange.order.model;

import java.io.Serializable;

public class EmailResponse implements Serializable {
    private static final long serialVersionUID = -1769736417984864802L;

    private boolean successful;

    private boolean message;

    public boolean isSuccessful() {
        return successful;
    }

    public void setSuccessful(boolean successful) {
        this.successful = successful;
    }

    public boolean isMessage() {
        return message;
    }

    public void setMessage(boolean message) {
        this.message = message;
    }
}
