package com.cwt.bpg.cbt.exchange.order.model;

import java.io.Serializable;
import java.util.List;

public class Client implements Serializable {

	private static final long serialVersionUID = 4416580993384869665L;
	
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
}
