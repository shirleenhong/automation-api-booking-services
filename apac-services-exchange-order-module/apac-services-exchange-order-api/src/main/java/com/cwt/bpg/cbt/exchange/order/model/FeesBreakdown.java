package com.cwt.bpg.cbt.exchange.order.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class FeesBreakdown implements Serializable {

	private static final long serialVersionUID = 5067269385200872058L;

	protected BigDecimal commission;

	public BigDecimal getCommission() {
		return commission;
	}

	public void setCommission(BigDecimal commission) {
		this.commission = commission;
	}

}
