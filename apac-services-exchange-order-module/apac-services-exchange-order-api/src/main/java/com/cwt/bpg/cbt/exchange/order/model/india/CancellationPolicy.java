package com.cwt.bpg.cbt.exchange.order.model.india;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;

public class CancellationPolicy implements Serializable {

	private static final long serialVersionUID = -4342985743603042736L;

	@ApiModelProperty(allowableValues = "Duration, SpecialRate, Others")
	@NotNull
	private CancellationPolicyType type;

	@ApiModelProperty(value = "Should only contain value when cancellation policy type = 'Duration'.")
	private Integer number;

	@ApiModelProperty(value = "Should only contain value when cancellation policy type = 'Duration'.", allowableValues = "Days, Hrs")
	private Unit unit;

	@ApiModelProperty(value = "Should only contain value when cancellation policy type = 'Others'.")
	private String description;

	public CancellationPolicyType getType() {
		return type;
	}

	public void setType(CancellationPolicyType type) {
		this.type = type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public Unit getUnit() {
		return unit;
	}

	public void setUnit(Unit unit) {
		this.unit = unit;
	}
}
