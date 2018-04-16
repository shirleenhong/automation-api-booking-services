package com.cwt.bpg.cbt.exchange.order;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cwt.bpg.cbt.exchange.order.model.Product;
import com.cwt.bpg.cbt.exchange.order.model.ProductList;
import com.cwt.bpg.cbt.exchange.order.model.Vendor;
import com.cwt.bpg.cbt.mongodb.config.MongoDbConnection;
import com.cwt.bpg.cbt.mongodb.config.mapper.DBObjectMapper;
import com.mongodb.client.FindIterable;

@Service
public class ExchangeOrderImpl implements ExchangeOrderApi {
	
	@Autowired
	private MongoDbConnection mongoDbConnection;

	@Autowired
	private DBObjectMapper dBObjectMapper;
	
	@Override
	public List<Product> getProducts(String countryCode) {
		List<Product> products = new ArrayList<Product>();
		
		FindIterable iterable = mongoDbConnection.getCollection(Product.COLLECTION).find(new Document("countryCode",countryCode));
		try {
			ProductList productList = dBObjectMapper.mapDocumentToBean((Document) iterable.first(), ProductList.class);
			if (productList != null) {
				products.addAll(productList.getProducts());
				sort(products);
			}
		} catch (IOException e) {
			System.out.println("error");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return products;
	}
	
	private void sort(List<Product> products) {
		for(Product product: products) {
			product.getVendors().sort(Comparator.comparing(Vendor::getVendorName));
		}
		products.sort(Comparator.comparing(Product::getProductName));
	}
}
