package com.cwt.bpg.cbt.exchange.order.model.india;

import java.io.Serializable;

public class IndiaInsurance implements Serializable {
	private static final long serialVersionUID = -2416249545097859674L;

	private String details;
	private String details1;
	private String details2;
	
	public String getDetails() {
		return details;
	}
	public void setDetails(String details) {
		this.details = details;
	}
	public String getDetails1() {
		return details1;
	}
	public void setDetails1(String details1) {
		this.details1 = details1;
	}
	public String getDetails2() {
		return details2;
	}
	public void setDetails2(String details2) {
		this.details2 = details2;
	}
}
