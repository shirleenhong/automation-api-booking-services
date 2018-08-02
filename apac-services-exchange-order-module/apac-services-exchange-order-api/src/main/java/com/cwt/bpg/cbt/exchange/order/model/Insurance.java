package com.cwt.bpg.cbt.exchange.order.model;

import java.io.Serializable;
import java.util.Date;

public class Insurance implements Serializable {
	private static final long serialVersionUID = -2416249545097859674L;

	private String geographicalArea;
	private int noOfDays;
	private Date fromDate;
	private String plan;
	private Insured insured;

	public String getGeographicalArea() {
		return geographicalArea;
	}

	public void setGeographicalArea(String geographicalArea) {
		this.geographicalArea = geographicalArea;
	}

	public int getNoOfDays() {
		return noOfDays;
	}

	public void setNoOfDays(int noOfDays) {
		this.noOfDays = noOfDays;
	}

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public String getPlan() {
		return plan;
	}

	public void setPlan(String plan) {
		this.plan = plan;
	}

	public Insured getInsured() {
		return insured;
	}

	public void setInsured(Insured insured) {
		this.insured = insured;
	}

}
