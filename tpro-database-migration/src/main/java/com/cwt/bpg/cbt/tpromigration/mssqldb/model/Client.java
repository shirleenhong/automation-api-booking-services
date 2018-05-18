package com.cwt.bpg.cbt.tpromigration.mssqldb.model;

import java.util.List;

public class Client {

	private int clientId;
	private List<ProductMerchantFee> products;
	private List<BankVendor> vendors;
	private List<Bank> banks;

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
	
	

}
