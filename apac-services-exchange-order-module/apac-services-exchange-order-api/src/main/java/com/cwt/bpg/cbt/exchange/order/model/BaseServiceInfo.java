package com.cwt.bpg.cbt.exchange.order.model;

import java.io.Serializable;

public class BaseServiceInfo implements Serializable {

	private static final long serialVersionUID = -6635160737347930743L;

	private FormOfPayment formOfPayment;

	public FormOfPayment getFormOfPayment() {
		return formOfPayment;
	}

	public void setFormOfPayment(FormOfPayment formOfPayment) {
		this.formOfPayment = formOfPayment;
	}
}
