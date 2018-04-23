package com.cwt.bpg.cbt.exchange.order;

import java.util.List;

import com.cwt.bpg.cbt.exchange.order.model.Product;

public interface ProductsApi {
	
	List<Product> getProducts(String countryCode);

}
