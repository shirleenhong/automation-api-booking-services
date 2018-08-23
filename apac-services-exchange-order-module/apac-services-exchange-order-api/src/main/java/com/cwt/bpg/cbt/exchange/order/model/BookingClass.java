package com.cwt.bpg.cbt.exchange.order.model;

import java.io.Serializable;

public class BookingClass implements Serializable{
	
	private static final long serialVersionUID = 8211907207892083964L;

	private String code;
	
	private String description;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
}
