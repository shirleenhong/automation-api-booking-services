package com.cwt.bpg.cbt.exchange.order.model;

import java.math.BigDecimal;

public class MiscFeesBreakdown extends FeesBreakdown {

	private static final long serialVersionUID = 3847345778240245241L;

	private BigDecimal sellingPriceInDi;
	private BigDecimal gstAmount;
	private BigDecimal merchantFee;
	private BigDecimal nettCostGst;

	public MiscFeesBreakdown() {
		this.commission = BigDecimal.ZERO;
		this.gstAmount = BigDecimal.ZERO;
		this.nettCostGst = BigDecimal.ZERO;
		this.merchantFee = BigDecimal.ZERO;
		this.sellingPriceInDi = BigDecimal.ZERO;
	}

	public BigDecimal getSellingPriceInDi() {
		return sellingPriceInDi;
	}

	public void setSellingPriceInDi(BigDecimal sellingPriceInDi) {
		this.sellingPriceInDi = sellingPriceInDi;
	}

	public BigDecimal getGstAmount() {
		return gstAmount;
	}

	public void setGstAmount(BigDecimal gstAmount) {
		this.gstAmount = gstAmount;
	}

	public BigDecimal getMerchantFee() {
		return merchantFee;
	}

	public void setMerchantFee(BigDecimal merchantFee) {
		this.merchantFee = merchantFee;
	}

	public BigDecimal getNettCostGst() {
		return nettCostGst;
	}

	public void setNettCostGst(BigDecimal nettCostGst) {
		this.nettCostGst = nettCostGst;
	}
}
