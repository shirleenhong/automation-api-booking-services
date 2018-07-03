package com.cwt.bpg.cbt.exchange.order.model;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

public class NettCostInput implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(required = true)
	@NotNull
	protected BigDecimal sellingPrice;
	@ApiModelProperty(required = true)
	@NotNull
	private Double commissionPct;

	public BigDecimal getSellingPrice() {
		return sellingPrice;
	}

	public void setSellingPrice(BigDecimal sellingPrice) {
		this.sellingPrice = sellingPrice;
	}

	public Double getCommissionPct() {
		return commissionPct;
	}

	public void setCommissionPct(Double commissionPct) {
		this.commissionPct = commissionPct;
	}

}
