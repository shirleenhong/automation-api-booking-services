package com.cwt.bpg.cbt.exchange.order.model;

import java.math.BigDecimal;

public class IndiaNonAirFeesBreakdown extends NonAirFeesBreakdown {

	private static final long serialVersionUID = 3847345778240245241L;

	private BigDecimal commissionAmount;
	private BigDecimal discountAmount;
	private boolean noMerchantFee;
	private boolean clientExempt;
	
	public BigDecimal getCommissionAmount() {
		return commissionAmount;
	}
	public void setCommissionAmount(BigDecimal commissionAmount) {
		this.commissionAmount = commissionAmount;
	}
	public BigDecimal getDiscountAmount() {
		return discountAmount;
	}
	public void setDiscountAmount(BigDecimal discountAmount) {
		this.discountAmount = discountAmount;
	}

	public boolean isNoMerchantFee() {
		return noMerchantFee;
	}

	public void setNoMerchantFee(boolean noMerchantFee) {
		this.noMerchantFee = noMerchantFee;
	}

	public boolean isClientExempt() {
		return clientExempt;
	}

	public void setClientExempt(boolean clientExempt) {
		this.clientExempt = clientExempt;
	}
}
