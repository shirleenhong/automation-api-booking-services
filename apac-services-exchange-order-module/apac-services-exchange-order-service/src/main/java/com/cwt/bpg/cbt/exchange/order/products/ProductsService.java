package com.cwt.bpg.cbt.exchange.order.products;

import java.util.List;
import java.util.stream.Collectors;

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

		final List<Product> products = productRepo.getProducts(countryCode);

		final List<Product> filteredProducts = products.stream().filter(f -> f.getVendors().size() > 0)
				.collect(Collectors.toList());

		return filteredProducts;
	}
}
