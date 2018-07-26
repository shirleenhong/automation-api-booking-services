package com.cwt.bpg.cbt.exchange.order.model;

import java.util.Date;

public class EoInsurance {

	private String geographicalArea;
	private int noOfDays;
	private Date fromDate;
	private String plan;
	private EoInsured insured;

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

	public EoInsured getInsured() {
		return insured;
	}

	public void setInsured(EoInsured insured) {
		this.insured = insured;
	}

}
