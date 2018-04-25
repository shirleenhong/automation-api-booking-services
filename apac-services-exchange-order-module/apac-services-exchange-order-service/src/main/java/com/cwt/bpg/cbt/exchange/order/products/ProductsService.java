package com.cwt.bpg.cbt.exchange.order.products;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.cwt.bpg.cbt.exchange.order.ProductsRepository;
import com.cwt.bpg.cbt.exchange.order.model.Product;

@Service
public class ProductsService {

	@Autowired 
	private ProductsRepository productRepo;
	
	@Cacheable("products")
	public List<Product> getProducts(String countryCode) {
		
		return productRepo.getProducts(countryCode);
	}

}
