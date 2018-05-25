package com.cwt.bpg.cbt.tpromigration.mssqldb.model;

import java.util.List;

public class Client {

	private int clientId;
	private int cmpid;
	private String name;
	private String profileName;
	private Integer pricingId;
	private Boolean exemptTax;
	private List<ProductMerchantFee> mfProducts;
	private List<CreditCardVendor> mfCcs;
	private List<Bank> mfBanks;
	private List<ClientPricing> clientPricings;
	private Boolean standardMfProduct;
	private Boolean applyMfCc;
	private Boolean applyMfBank;
	private Double merchantFee;
	private Boolean lccSameAsInt;
	private String lccDdlFeeApply;
	private String intDdlFeeApply;

	public int getClientId() {
		return clientId;
	}

	public void setClientId(int clientId) {
		this.clientId = clientId;
	}

	public int getCmpid() {
		return cmpid;
	}

	public void setCmpid(int cmpid) {
		this.cmpid = cmpid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getProfileName() {
		return profileName;
	}

	public void setProfileName(String profileName) {
		this.profileName = profileName;
	}

	public Integer getPricingId() {
		return pricingId;
	}

	public void setPricingId(Integer pricingId) {
		this.pricingId = pricingId;
	}

	public Boolean getExemptTax() {
		return exemptTax;
	}

	public void setExemptTax(Boolean exemptTax) {
		this.exemptTax = exemptTax;
	}

	public List<ClientPricing> getClientPricings() {
		return clientPricings;
	}

	public void setClientPricings(List<ClientPricing> clientPricings) {
		this.clientPricings = clientPricings;
	}

	public Boolean getStandardMfProduct() {
		return standardMfProduct;
	}

	public void setStandardMfProduct(Boolean standardMfProduct) {
		this.standardMfProduct = standardMfProduct;
	}

	public Boolean getApplyMfCc() {
		return applyMfCc;
	}

	public void setApplyMfCc(Boolean applyMfCc) {
		this.applyMfCc = applyMfCc;
	}

	public Boolean getApplyMfBank() {
		return applyMfBank;
	}

	public void setApplyMfBank(Boolean applyMfBank) {
		this.applyMfBank = applyMfBank;
	}

	public Double getMerchantFee() {
		return merchantFee;
	}

	public void setMerchantFee(Double merchantFee) {
		this.merchantFee = merchantFee;
	}

	public Boolean getLccSameAsInt() {
		return lccSameAsInt;
	}

	public void setLccSameAsInt(Boolean lccSameAsInt) {
		this.lccSameAsInt = lccSameAsInt;
	}

	public String getLccDdlFeeApply() {
		return lccDdlFeeApply;
	}

	public void setLccDdlFeeApply(String lccDdlFeeApply) {
		this.lccDdlFeeApply = lccDdlFeeApply;
	}

	public String getIntDdlFeeApply() {
		return intDdlFeeApply;
	}

	public void setIntDdlFeeApply(String intDdlFeeApply) {
		this.intDdlFeeApply = intDdlFeeApply;
	}

	public List<ProductMerchantFee> getMfProducts() {
		return mfProducts;
	}

	public void setMfProducts(List<ProductMerchantFee> mfProducts) {
		this.mfProducts = mfProducts;
	}

	public List<Bank> getMfBanks() {
		return mfBanks;
	}

	public void setMfBanks(List<Bank> mfBanks) {
		this.mfBanks = mfBanks;
	}

	public List<CreditCardVendor> getMfCcs() {
		return mfCcs;
	}

	public void setMfCcs(List<CreditCardVendor> mfCcs) {
		this.mfCcs = mfCcs;
	}
}
