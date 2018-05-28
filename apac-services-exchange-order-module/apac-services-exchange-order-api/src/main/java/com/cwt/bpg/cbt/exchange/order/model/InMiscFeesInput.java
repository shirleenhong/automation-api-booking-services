package com.cwt.bpg.cbt.exchange.order.model;

import java.io.Serializable;
import java.math.BigDecimal;

import org.hibernate.validator.constraints.NotEmpty;

public class InMiscFeesInput implements Serializable
{
	private static final long serialVersionUID = 7271039286429340584L;

	private String fopType;
	private String countryCode;
	
	private boolean commissionByPercent;
	private BigDecimal costAmount;
	private Double commissionPercent;
	private BigDecimal commission;
	private Double discountPercent;
	private BigDecimal discount;
	private boolean discountByPercent;
	private int fopMode;
	private InProduct product;
	private String acctType;
	private String fopNumber;
	
	@NotEmpty
	private String profileName;
	
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

	public InProduct getProduct() {
		return product;
	}

	public void setProduct(InProduct product) {
		this.product = product;
	}

	public String getFopType() {
		return fopType;
	}

	public void setFopType(String fopType) {
		this.fopType = fopType;
	}

	public String getProfileName() {
		return profileName;
	}

	public void setProfileName(String profileName) {
		this.profileName = profileName;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
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
