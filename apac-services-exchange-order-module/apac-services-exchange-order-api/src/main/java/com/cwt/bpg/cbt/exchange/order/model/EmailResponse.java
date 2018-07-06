package com.cwt.bpg.cbt.exchange.order.model;

import java.io.Serializable;

public class EmailResponse implements Serializable {
	private static final long serialVersionUID = -1769736417984864802L;

	private boolean success;

	private String message;

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
