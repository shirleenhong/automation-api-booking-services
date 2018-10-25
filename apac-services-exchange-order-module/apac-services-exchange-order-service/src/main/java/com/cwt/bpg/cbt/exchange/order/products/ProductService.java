package com.cwt.bpg.cbt.exchange.order.products;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.cwt.bpg.cbt.calculator.model.Country;
import com.cwt.bpg.cbt.exchange.order.ProductRepository;
import com.cwt.bpg.cbt.exchange.order.model.BaseProduct;
import com.cwt.bpg.cbt.exchange.order.model.IndiaProduct;
import com.cwt.bpg.cbt.exchange.order.model.Product;
import com.cwt.bpg.cbt.exchange.order.model.Vendor;

@Service
public class ProductService {

	@Autowired
	private ProductRepository repository;

	@Cacheable(cacheNames = "products", key = "#countryCode")
	public List<BaseProduct> getProducts(String countryCode) {

		final List<BaseProduct> baseProducts = repository.getProducts(countryCode);

		return baseProducts.stream().filter(f -> !f.getVendors().isEmpty())
				.collect(Collectors.toList());
	}

	@CacheEvict(cacheNames = "products", key = "#countryCode")
	public String saveProduct(String countryCode, Product product, boolean insertFlag) {
		return repository.saveProduct(countryCode, product, insertFlag);
	}

	@CacheEvict(cacheNames = "products", key = "IN")
	public String saveIndiaProduct(IndiaProduct product, boolean insertFlag) {
		return repository.saveProduct(Country.INDIA.getCode(), product, insertFlag);
	}

	@Cacheable(cacheNames="products", key="{#countryCode, #productCode}")
	public BaseProduct getProductByCode(String countryCode, String productCode) {

		List<BaseProduct> baseProducts = getProducts(countryCode);
		Optional<BaseProduct> product = baseProducts.stream()
						.filter(p -> p.getProductCode().equalsIgnoreCase(productCode))
						.findFirst();

		return product.orElse(null);

	}

	public Vendor getVendor(String countryCode, String productCode, String vendorCode) {

		BaseProduct product = getProductByCode(countryCode, productCode);
		Optional<Vendor> vendor = Optional.empty();

		if (product != null) {
			vendor = product.getVendors().stream()
					.filter(v -> v.getCode().equalsIgnoreCase(vendorCode))
					.findFirst();
		}

		return vendor.orElse(null);
	}
	
	@CacheEvict(cacheNames = "products", allEntries = true)
	public String removeProduct(String countryCode, String productCode) {
		return repository.removeProduct(countryCode, productCode);
	}
	
	@CacheEvict(cacheNames = "products", allEntries = true)
	public String removeVendor(String countryCode, String vendorCode) {
		return repository.removeVendor(countryCode, vendorCode);
	}
}
