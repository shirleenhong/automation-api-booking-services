package com.cwt.bpg.cbt.exchange.order.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class VisaInput extends OtherServiceFeesInput implements Serializable {
	private boolean nettCostMerchantFeeChecked;
	private boolean cwtHandlingMerchantFeeChecked;
	private BigDecimal cwtHandling;
	private BigDecimal vendorHandling;
	private BigDecimal commission;

	public boolean isNettCostMerchantFeeChecked() {
		return nettCostMerchantFeeChecked;
	}

	public void setNettCostMerchantFeeChecked(boolean nettCostMerchantFeeChecked) {
		this.nettCostMerchantFeeChecked = nettCostMerchantFeeChecked;
	}

	public boolean isCwtHandlingMerchantFeeChecked() {
		return cwtHandlingMerchantFeeChecked;
	}

	public void setCwtHandlingMerchantFeeChecked(boolean cwtHandlingMerchantFeeChecked) {
		this.cwtHandlingMerchantFeeChecked = cwtHandlingMerchantFeeChecked;
	}

	public BigDecimal getCwtHandling() {
		return cwtHandling;
	}

	public void setCwtHandling(BigDecimal cwtHandling) {
		this.cwtHandling = cwtHandling;
	}

	public BigDecimal getVendorHandling() {
		return vendorHandling;
	}

	public void setVendorHandling(BigDecimal vendorHandling) {
		this.vendorHandling = vendorHandling;
	}

	public BigDecimal getCommission() {
		return commission;
	}

	public void setCommission(BigDecimal commission) {
		this.commission = commission;
	}
}
