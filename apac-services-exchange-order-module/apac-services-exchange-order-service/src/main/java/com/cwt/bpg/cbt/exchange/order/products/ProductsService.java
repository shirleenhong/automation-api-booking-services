package com.cwt.bpg.cbt.exchange.order.products;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.cwt.bpg.cbt.exchange.order.ProductRepository;
import com.cwt.bpg.cbt.exchange.order.model.Product;

@Service
public class ProductsService {

	@Autowired
	private ProductRepository productRepo;

	@Cacheable("products")
	public List<Product> getProducts(String countryCode) {

		final List<Product> products = productRepo.getProducts(countryCode);

		return products.stream().filter(f -> !f.getVendors().isEmpty())
				.collect(Collectors.toList());
	}
}
