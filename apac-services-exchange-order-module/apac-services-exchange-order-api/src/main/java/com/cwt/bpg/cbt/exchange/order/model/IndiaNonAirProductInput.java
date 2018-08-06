package com.cwt.bpg.cbt.exchange.order.model;

import java.io.Serializable;

import org.hibernate.validator.constraints.NotEmpty;

import io.swagger.annotations.ApiModelProperty;

public class IndiaNonAirProductInput implements Serializable {

	private static final long serialVersionUID = -6696859941182854729L;

	private Double ot1Percent;
	private Double ot2Percent;
	private Double gstPercent;

	@ApiModelProperty(required = true)
	@NotEmpty
	private String productCode;

	public Double getOt1Percent() {
		return ot1Percent;
	}

	public void setOt1Percent(Double ot1Percent) {
		this.ot1Percent = ot1Percent;
	}

	public Double getOt2Percent() {
		return ot2Percent;
	}

	public void setOt2Percent(Double ot2Percent) {
		this.ot2Percent = ot2Percent;
	}

	public Double getGstPercent() {
		return gstPercent;
	}

	public void setGstPercent(Double gstPercent) {
		this.gstPercent = gstPercent;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
}
