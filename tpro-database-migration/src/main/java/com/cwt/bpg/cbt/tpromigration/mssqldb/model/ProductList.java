package com.cwt.bpg.cbt.tpromigration.mssqldb.model;

import java.util.List;

public class ProductList {
	
	private String countryCode;
	
	private List<Product> products;

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}
	
	

}
