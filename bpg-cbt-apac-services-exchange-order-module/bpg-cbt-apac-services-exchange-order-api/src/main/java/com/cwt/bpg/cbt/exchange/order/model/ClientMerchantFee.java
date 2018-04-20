package com.cwt.bpg.cbt.exchange.order.model;

import java.io.Serializable;
import java.util.List;

public class ClientMerchantFee implements Serializable {
	
	private static final long serialVersionUID = -4220085014424481443L;

	private String countryCode;
	
	private List<MerchantFee> merchantFees;

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public List<MerchantFee> getMerchantFees() {
		return merchantFees;
	}

	public void setMerchantFees(List<MerchantFee> merchantFees) {
		this.merchantFees = merchantFees;
	}
}
