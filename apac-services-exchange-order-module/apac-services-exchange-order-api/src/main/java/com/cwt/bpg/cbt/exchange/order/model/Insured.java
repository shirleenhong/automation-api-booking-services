package com.cwt.bpg.cbt.exchange.order.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class Insured implements Serializable {

	private static final long serialVersionUID = -7786999078858178621L;

	private String name;
	private String relationship;
	private BigDecimal premiumAmount;
	private String address;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRelationship() {
		return relationship;
	}

	public void setRelationship(String relationship) {
		this.relationship = relationship;
	}

	public BigDecimal getPremiumAmount() {
		return premiumAmount;
	}

	public void setPremiumAmount(BigDecimal premiumAmount) {
		this.premiumAmount = premiumAmount;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

}
