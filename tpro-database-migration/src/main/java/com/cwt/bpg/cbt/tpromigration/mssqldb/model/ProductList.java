package com.cwt.bpg.cbt.tpromigration.mssqldb.model;

import java.util.List;

public class ProductList<T> {
	
	private String countryCode;
	
	private List<T> products;

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public List<T> getProducts() {
		return products;
	}

	public void setProducts(List<T> products) {
		this.products = products;
	}
}
