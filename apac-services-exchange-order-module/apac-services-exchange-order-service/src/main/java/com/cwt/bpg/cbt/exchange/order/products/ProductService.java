package com.cwt.bpg.cbt.exchange.order.products;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.cwt.bpg.cbt.exchange.order.ProductRepository;
import com.cwt.bpg.cbt.exchange.order.model.BaseProduct;
import com.cwt.bpg.cbt.exchange.order.model.Product;
import com.cwt.bpg.cbt.exchange.order.model.Vendor;

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

		return product.orElse(null);

	}

	public Vendor getVendor(String productCode, String vendorCode) {
		
		Product product = getProductByCodeVendorCode(productCode, vendorCode);
		Optional<Vendor> vendor = product.getVendors().stream()
				.filter(v -> v.getVendorNumber().equalsIgnoreCase(vendorCode))
				.findFirst();

		return vendor.orElse(null);
	}

	private Product getProductByCodeVendorCode(String productCode,
			String vendorCode) {

		List<Product> productList = productRepo.getProductsByCodeVendorCode(productCode,
				vendorCode);
		Optional<Product> product = productList.stream()
				.filter(p -> p.getProductCode().equalsIgnoreCase(productCode))
				.findFirst();

		return product.orElse(null);
	}
	
}
