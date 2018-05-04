package com.cwt.bpg.cbt.exchange.order.model;

import org.mongodb.morphia.annotations.Entity;

@Entity("insurance")
public class Insurance {

	private String type;
	private Integer commission;

	public Integer getCommission() {
		return commission;
	}

	public void setCommission(Integer commission) {
		this.commission = commission;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
