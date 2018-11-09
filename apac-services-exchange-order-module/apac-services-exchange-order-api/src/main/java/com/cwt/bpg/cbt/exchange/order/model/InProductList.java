package com.cwt.bpg.cbt.exchange.order.model;

import java.util.List;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Field;
import org.mongodb.morphia.annotations.Index;
import org.mongodb.morphia.annotations.Indexes;

@Entity("productList")
@Indexes(@Index(fields = @Field("countryCode")))
public class InProductList extends ProductList {

	private List<IndiaProduct> products;

	@Override
	public List<IndiaProduct> getProducts() {
		return products;
	}

	public void setProducts(List<IndiaProduct> products) {
		this.products = products;
	}
}
