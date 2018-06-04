package com.cwt.bpg.cbt.exchange.order.model;

import java.math.BigDecimal;

import org.hibernate.validator.constraints.NotEmpty;

public class AirFeesInput extends FeesInput {

	private static final long serialVersionUID = 4361130224212373736L;

	@NotEmpty
	private String fopType;
	private BigDecimal sellingPrice;
	private boolean isMerchantFeeWaive;
	private boolean applyFormula;
	private boolean commissionByPercent;
	private boolean discountByPercent;
	// TODO : should inform Power Express to pass true if all items are selected
	private boolean webFareSelected;
	private boolean cwtAbsorb;
	private boolean uatp;
	private BigDecimal nettFare;
	private BigDecimal discount;
	private Double discountPercent;
	private BigDecimal tax1;
	private BigDecimal tax2;
	private BigDecimal merchantFee;
	private BigDecimal commission;
	private BigDecimal transactionFee;
	private Double commissionPercent;
	private String productType;

	public AirFeesInput() {
		this.sellingPrice = BigDecimal.ZERO;
	}

	public String getFopType() {
		return fopType;
	}

	public void setFopType(String fopType) {
		this.fopType = fopType;
	}

	public BigDecimal getSellingPrice() {
		return sellingPrice;
	}

	public void setSellingPrice(BigDecimal sellingPrice) {
		this.sellingPrice = sellingPrice;
	}

	public boolean isMerchantFeeWaive() {
		return isMerchantFeeWaive;
	}

	public void setMerchantFeeWaive(boolean merchantFeeWaive) {
		isMerchantFeeWaive = merchantFeeWaive;
	}

	public boolean isApplyFormula() {
		return applyFormula;
	}

	public void setApplyFormula(boolean applyFormula) {
		this.applyFormula = applyFormula;
	}

	public boolean isCommissionByPercent() {
		return commissionByPercent;
	}

	public void setCommissionByPercent(boolean commissionByPercent) {
		this.commissionByPercent = commissionByPercent;
	}

	public boolean isDiscountByPercent() {
		return discountByPercent;
	}

	public void setDiscountByPercent(boolean discountByPercent) {
		this.discountByPercent = discountByPercent;
	}

	public boolean isWebFareSelected() {
		return webFareSelected;
	}

	public void setWebFareSelected(boolean webFareSelected) {
		this.webFareSelected = webFareSelected;
	}

	public boolean isCwtAbsorb() {
		return cwtAbsorb;
	}

	public void setCwtAbsorb(boolean cwtAbsorb) {
		this.cwtAbsorb = cwtAbsorb;
	}

	public boolean isUatp() {
		return uatp;
	}

	public void setUatp(boolean uatp) {
		this.uatp = uatp;
	}

	public BigDecimal getNettFare() {
		return nettFare;
	}

	public void setNettFare(BigDecimal nettFare) {
		this.nettFare = nettFare;
	}

	public BigDecimal getDiscount() {
		return discount;
	}

	public void setDiscount(BigDecimal discount) {
		this.discount = discount;
	}

	public BigDecimal getTax1() {
		return tax1;
	}

	public void setTax1(BigDecimal tax1) {
		this.tax1 = tax1;
	}

	public BigDecimal getTax2() {
		return tax2;
	}

	public void setTax2(BigDecimal tax2) {
		this.tax2 = tax2;
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

	public BigDecimal getTransactionFee() {
		return transactionFee;
	}

	public void setTransactionFee(BigDecimal transactionFee) {
		this.transactionFee = transactionFee;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public Double getDiscountPercent() {
		return discountPercent;
	}

	public void setDiscountPercent(Double discountPercent) {
		this.discountPercent = discountPercent;
	}

	public Double getCommissionPercent() {
		return commissionPercent;
	}

	public void setCommissionPercent(Double commissionPercent) {
		this.commissionPercent = commissionPercent;
	}

}
