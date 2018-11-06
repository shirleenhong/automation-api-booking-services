package com.cwt.bpg.cbt.exchange.order.model;

import java.util.List;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Field;
import org.mongodb.morphia.annotations.Index;
import org.mongodb.morphia.annotations.Indexes;

@Entity("productList")
@Indexes(@Index(fields = @Field("countryCode")))
public class HkSgProductList extends ProductList {

	private List<Product> products;

	@Override
	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}
}
