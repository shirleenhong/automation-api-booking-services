package com.cwt.bpg.cbt.exchange.order.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class FeesBreakdown implements Serializable {
		
	private static final long serialVersionUID = 5067269385200872058L;
	
	protected BigDecimal sellingPriceInDi;
	protected BigDecimal gstAmount;
	protected BigDecimal merchantFee;
	protected BigDecimal commission;
	protected BigDecimal nettCostGst;
	
	public FeesBreakdown () {
		
		this.gstAmount = BigDecimal.ZERO;
		this.nettCostGst =  BigDecimal.ZERO;
		this.merchantFee =  BigDecimal.ZERO;
		this.sellingPriceInDi =  BigDecimal.ZERO;
		this.commission =  BigDecimal.ZERO;		
	}
	
	public FeesBreakdown(BigDecimal sellingPriceInDi, BigDecimal gstAmount, BigDecimal merchantFee,
			BigDecimal commission, BigDecimal nettCostGst) {
		super();
		this.sellingPriceInDi = sellingPriceInDi;
		this.gstAmount = gstAmount;
		this.merchantFee = merchantFee;
		this.commission = commission;
		this.nettCostGst = nettCostGst;
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
	public BigDecimal getCommission() {
		return commission;
	}
	public void setCommission(BigDecimal commission) {
		this.commission = commission;
	}
	public BigDecimal getNettCostGst() {
		return nettCostGst;
	}
	public void setNettCostGst(BigDecimal nettCostGst) {
		this.nettCostGst = nettCostGst;
	}	
}
