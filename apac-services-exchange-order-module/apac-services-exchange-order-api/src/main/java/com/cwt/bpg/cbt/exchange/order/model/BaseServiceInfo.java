package com.cwt.bpg.cbt.exchange.order.model;

public abstract class BaseServiceInfo {

    private FormOfPayment formOfPayment;

    public FormOfPayment getFormOfPayment() {
        return formOfPayment;
    }

    public void setFormOfPayment(FormOfPayment formOfPayment) {
        this.formOfPayment = formOfPayment;
    }
}
