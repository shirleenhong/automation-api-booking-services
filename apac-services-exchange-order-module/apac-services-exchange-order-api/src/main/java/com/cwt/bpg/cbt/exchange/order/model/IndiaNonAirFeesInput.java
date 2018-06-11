package com.cwt.bpg.cbt.exchange.order.model;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;
import io.swagger.annotations.ApiModelProperty;

public class IndiaNonAirFeesInput extends FeesInput {
	private static final long serialVersionUID = 7271039286429340584L;

	private String fopType;
	private boolean commissionByPercent;
	@ApiModelProperty(required = true)
	@NotNull
	private BigDecimal costAmount;
	private Double commissionPercent;
	private BigDecimal commission;
	private Double discountPercent;
	private BigDecimal discount;
	private boolean discountByPercent;
	private int fopMode;
	@ApiModelProperty(required = true)
	@NotNull
	private IndiaProduct product;
	private String ccType;
	private String fopNumber;
	
	public String getCcType() {
		return ccType;
	}

	public void setCcType(String ccType) {
		this.ccType = ccType;
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

	public IndiaProduct getProduct() {
		return product;
	}

	public void setProduct(IndiaProduct product) {
		this.product = product;
	}

	public String getFopType() {
		return fopType;
	}

	public void setFopType(String fopType) {
		this.fopType = fopType;
	}

	public String getFopNumber() {
		return fopNumber;
	}

	public void setFopNumber(String fopNumber) {
		this.fopNumber = fopNumber;
	}

	public BigDecimal getCommission() {
		return commission;
	}

	public void setCommission(BigDecimal commission) {
		this.commission = commission;
	}

	public BigDecimal getDiscount() {
		return discount;
	}

	public void setDiscount(BigDecimal discount) {
		this.discount = discount;
	}
}