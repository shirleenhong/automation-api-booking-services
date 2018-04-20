package com.cwt.bpg.cbt.tpromigration.mssqldb.model;

import java.util.List;

public class MerchantFeeList {
	
	private String countryCode;
	
	private List<ClientMerchantFee> merchantFees;

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public List<ClientMerchantFee> getMerchantFees() {
		return merchantFees;
	}

	public void setMerchantFees(List<ClientMerchantFee> merchantFees) {
		this.merchantFees = merchantFees;
	}
	
	

}
