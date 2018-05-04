package com.cwt.bpg.cbt.exchange.order.model;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

public class NettCostInput {

	@NotNull
	protected BigDecimal sellingPrice;

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
