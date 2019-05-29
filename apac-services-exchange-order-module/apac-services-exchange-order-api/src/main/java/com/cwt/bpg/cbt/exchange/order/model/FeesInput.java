package com.cwt.bpg.cbt.exchange.order.model;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotEmpty;

public class FeesInput implements Serializable {

	private static final long serialVersionUID = -5237125856544162255L;

	@ApiModelProperty(required = true)
	@NotEmpty
	private String clientAccountNumber;

	public String getClientAccountNumber() {
		return clientAccountNumber;
	}

	public void setClientAccountNumber(String clientAccountNumber) {
		this.clientAccountNumber = clientAccountNumber;
	}
}
