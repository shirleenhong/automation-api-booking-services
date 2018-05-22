package com.cwt.bpg.cbt.exchange.order;

import static java.util.Comparator.comparing;

import static java.lang.String.CASE_INSENSITIVE_ORDER;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cwt.bpg.cbt.exchange.order.model.Product;
import com.cwt.bpg.cbt.exchange.order.model.ProductList;
import com.cwt.bpg.cbt.exchange.order.model.Vendor;
import com.cwt.bpg.cbt.mongodb.config.MorphiaComponent;

@Repository
public class ProductsRepository {

	private static final Logger LOGGER = LoggerFactory.getLogger(ProductsRepository.class); 
	
	@Autowired
	private MorphiaComponent morphia;

	public List<Product> getProducts(String countryCode) {
		
		List<Product> products = new ArrayList<>();
		ProductList productList = null;
		try {
					
			productList =  morphia.getDatastore().createQuery(ProductList.class)
					.field("countryCode")
					.equal(countryCode)
					.get();
		
		} catch(Exception e) {
			LOGGER.error("Unable to parse product list for {} {}", countryCode, e.getMessage()); 
		}
		
		if (productList != null) {
			products.addAll(productList.getProducts());
			sort(products);
		}
	
		return products;
	}

	private void sort(List<Product> products) {
		if (products != null && !products.isEmpty()) {
			for (Product product : products) {
				if (product.getVendors() != null && !product.getVendors().isEmpty()) {
					product.getVendors().sort(comparing(Vendor::getVendorName, CASE_INSENSITIVE_ORDER));
				}
			}
			products.sort(comparing(Product::getDescription, CASE_INSENSITIVE_ORDER));
		}

	}
}
