package com.cwt.bpg.cbt.exchange.order.model;

import java.math.BigDecimal;

public class InMiscFeesInput extends MiscFeesInput
{
	private static final long serialVersionUID = 7271039286429340584L;

	private boolean commissionByPercent;
	private BigDecimal costAmount;
	private Double commissionPercent;
	private Double discountPercent;	
	private boolean discountByPercent;
	private int fopMode;
	private Product product;
	private String acctType;
	
	public String getAcctType() {
		return acctType;
	}

	public void setAcctType(String acctType) {
		this.acctType = acctType;
	}
	
	public boolean isCommissionByPercent() {
		return commissionByPercent;
	}

	public void setCommissionByPercent(boolean commissionByPercent) {
		this.commissionByPercent = commissionByPercent;
	}

	public Double getCommissionPercent() {
		return commissionPercent;
	}

	public void setCommissionPercent(Double commissionPercent) {
		this.commissionPercent = commissionPercent;
	}

	public boolean isDiscountByPercent() {
		return discountByPercent;
	}

	public void setDiscountByPercent(boolean discountByPercent) {
		this.discountByPercent = discountByPercent;
	}

	public BigDecimal getCostAmount() {
		return costAmount;
	}

	public void setCostAmount(BigDecimal costAmount) {
		this.costAmount = costAmount;
	}

	public Double getDiscountPercent() {
		return discountPercent;
	}

	public void setDiscountPercent(Double discountPercent) {
		this.discountPercent = discountPercent;
	}

	public int getFopMode() {
		return fopMode;
	}

	public void setFopMode(int fopMode) {
		this.fopMode = fopMode;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}
}
