package com.cwt.bpg.cbt.tpromigration.mssqldb.model;

import java.util.List;

public class Client {

	private int clientId;
	private int cmpid;
	private String name;
	private String profileName;
	private Integer pricingId;
	private Boolean exemptTax;
	private List<ProductMerchantFee> products;
	private List<BankVendor> vendors;
	private List<Bank> banks;
	private List<ClientPricing> clientPricings;
	private Boolean standardMfProduct;
	private Boolean applyMfCc;
	private Boolean applyMfBank;
	private Double merchantFee;

	public void setProducts(List<ProductMerchantFee> list) {
		this.products = list;
	}

	public void setVendors(List<BankVendor> list) {
		this.vendors = list;
	}

	public void setBanks(List<Bank> list) {
		this.banks = list;
	}

	public List<ProductMerchantFee> getProducts() {
		return products;
	}

	public List<BankVendor> getVendors() {
		return vendors;
	}

	public List<Bank> getBanks() {
		return banks;
	}

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
}
