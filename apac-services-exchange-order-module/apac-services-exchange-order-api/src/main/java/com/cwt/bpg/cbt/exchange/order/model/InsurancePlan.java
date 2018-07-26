package com.cwt.bpg.cbt.exchange.order.model;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;
import org.mongodb.morphia.annotations.Entity;

import io.swagger.annotations.ApiModelProperty;

@Entity(value = "insurance", noClassnameStored = true)
public class InsurancePlan implements Serializable {

	private static final long serialVersionUID = 5189419687629453470L;

	@NotEmpty
	@ApiModelProperty(required = true)
	private String type;
	
	@NotNull
	@ApiModelProperty(required = true)
	private Float commission;

	public Float getCommission() {
		return commission;
	}

	public void setCommission(Float commission) {
		this.commission = commission;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
