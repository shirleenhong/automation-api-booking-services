package com.cwt.bpg.cbt.exchange.order.model;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;

import com.cwt.bpg.cbt.exchange.order.model.deserializer.DateDeserializer;
import com.cwt.bpg.cbt.exchange.order.model.serializer.DateSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import io.swagger.annotations.ApiModelProperty;

public class Insurance implements Serializable {
	private static final long serialVersionUID = -2416249545097859674L;

	private String geographicalArea;
	private int noOfDays;
	
	@ApiModelProperty(value = "Date in UTC", example = "2008-05-29T00:00:00.000Z")
	@JsonSerialize(using = DateSerializer.class)
	@JsonDeserialize(using = DateDeserializer.class)
	private Instant fromDate;

	private String plan;
	private List<Insured> insured;

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
	
	public Instant getFromDate() {
		return fromDate;
	}
	
	public void setFromDate(Instant fromDate) {
		this.fromDate = fromDate;
	}

	public String getPlan() {
		return plan;
	}

	public void setPlan(String plan) {
		this.plan = plan;
	}

	public List<Insured> getInsured() {
		return insured;
	}

	public void setInsured(List<Insured> insured) {
		this.insured = insured;
	}

}
