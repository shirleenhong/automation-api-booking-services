package com.cwt.bpg.cbt.exchange.order;

import java.io.IOException;
import java.util.ArrayList;
import static java.util.Comparator.*;
import java.util.List;

import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cwt.bpg.cbt.exchange.order.model.Product;
import com.cwt.bpg.cbt.exchange.order.model.ProductList;
import com.cwt.bpg.cbt.exchange.order.model.Vendor;
import com.cwt.bpg.cbt.mongodb.config.MongoDbConnection;
import com.cwt.bpg.cbt.mongodb.config.mapper.DBObjectMapper;
import com.mongodb.client.FindIterable;


@Service
public class ProductsRepository implements ProductsApi {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ProductsRepository.class);
	
	@Autowired
	private MongoDbConnection mongoDbConnection;

	@Autowired
	private DBObjectMapper dBObjectMapper;
	
	@Override
	public List<Product> getProducts(String countryCode) {
		List<Product> products = new ArrayList<>();
		
		FindIterable<Document> iterable = mongoDbConnection.getCollection(Product.COLLECTION).find(new Document("countryCode", countryCode));

		try {
			ProductList productList = dBObjectMapper.mapDocumentToBean(iterable.first(), ProductList.class);
			if (productList != null) {
				products.addAll(productList.getProducts());
				sort(products);
			}
		} 
		catch (IOException e) {
			LOGGER.error("Unable to parse product list for {} {}", countryCode, e.getMessage());
		}
		
		return products;
	}
	
	private void sort(List<Product> products) {
		if(products != null && !products.isEmpty()) {
			for(Product product: products) {
				if(product.getVendors() != null && !product.getVendors().isEmpty()) {
					product.getVendors().sort(comparing(Vendor::getVendorNumber));
				}
				
			}
			products.sort(comparing(Product::getProductCode));
		}
		
	}
}
