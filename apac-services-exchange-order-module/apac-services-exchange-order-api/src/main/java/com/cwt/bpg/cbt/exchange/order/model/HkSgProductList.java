package com.cwt.bpg.cbt.exchange.order.model;

import java.util.List;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Field;
import org.mongodb.morphia.annotations.Index;
import org.mongodb.morphia.annotations.Indexes;

@Entity("productList")
@Indexes(@Index(fields = @Field("countryCode")))
public class HkSgProductList {
	private String countryCode;

	private List<HkSgProduct> products;

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public List<HkSgProduct> getProducts() {
		return products;
	}

	public void setProducts(List<HkSgProduct> products) {
		this.products = products;
	}
}
