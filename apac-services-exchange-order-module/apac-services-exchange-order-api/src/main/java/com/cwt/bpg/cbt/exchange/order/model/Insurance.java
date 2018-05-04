package com.cwt.bpg.cbt.exchange.order.model;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;
import org.mongodb.morphia.annotations.Entity;

@Entity("insurance")
public class Insurance {

	@NotEmpty
	private String type;
	
	@NotNull
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
