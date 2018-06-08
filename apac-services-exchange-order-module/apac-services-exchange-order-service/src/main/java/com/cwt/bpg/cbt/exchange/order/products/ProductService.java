package com.cwt.bpg.cbt.exchange.order.products;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.cwt.bpg.cbt.exchange.order.model.BaseProduct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.cwt.bpg.cbt.exchange.order.ProductRepository;

@Service
public class ProductService {

	@Autowired
	private ProductRepository productRepo;

	@Cacheable("products")
	public List<BaseProduct> getProducts(String countryCode) {

		final List<BaseProduct> baseProducts = productRepo.getProducts(countryCode);

		return baseProducts.stream().filter(f -> !f.getVendors().isEmpty())
				.collect(Collectors.toList());
	}
	
	@Cacheable(cacheNames="products", key="{#countryCode, #productCode}")
	public BaseProduct getProductByCode(String countryCode, String productCode) {
		
		List<BaseProduct> baseProducts = getProducts(countryCode);
		Optional<BaseProduct> product = baseProducts.stream()
						.filter(p -> p.getProductCode().equalsIgnoreCase(productCode))
						.findFirst();
		
		if(product.isPresent()) {
			return product.get();
		}
		
		return null;
	}
}
