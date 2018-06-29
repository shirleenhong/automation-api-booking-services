package com.cwt.bpg.cbt.exchange.order;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.mongodb.morphia.query.CriteriaContainer;
import org.mongodb.morphia.query.CriteriaContainerImpl;
import org.mongodb.morphia.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cwt.bpg.cbt.calculator.model.Country;
import com.cwt.bpg.cbt.exchange.order.model.*;
import com.cwt.bpg.cbt.mongodb.config.MorphiaComponent;

@Repository
public class ProductRepository {

	
	private static final Logger LOGGER = LoggerFactory.getLogger(ProductRepository.class);

	@Autowired
	private MorphiaComponent morphia;
	

	public List<BaseProduct> getProducts(String countryCode) {

		List<BaseProduct> baseProducts = new ArrayList<>();
		try {
			if (Country.INDIA.getCode().equalsIgnoreCase(countryCode)) {
				baseProducts.addAll(getIndiaProducts(countryCode));
			}
			else {
				baseProducts.addAll(getNonIndiaProducts(countryCode));
			}
			sort(baseProducts);
		}
		catch (Exception e) {
			LOGGER.error("Unable to parse product list for {} {}", countryCode, e.getMessage());
		}

		return baseProducts;
	}
	
	public List<BaseProduct> getHkSgProducts(String[] countryCodes) {

		List<BaseProduct> baseProducts = new ArrayList<>();
		baseProducts.addAll(getNonIndiaProducts(countryCodes));

		sort(baseProducts);
		
		return baseProducts;
	}

	private List<Product> getNonIndiaProducts(String countryCode) {
		HkSgProductList productList = morphia.getDatastore().createQuery(HkSgProductList.class)
				.field("countryCode").equalIgnoreCase(countryCode).get();

		return productList == null ? Collections.emptyList() : productList.getProducts();
	}

	private List<IndiaProduct> getIndiaProducts(String countryCode) {
		InProductList productList = morphia.getDatastore().createQuery(InProductList.class)
				.field("countryCode").equalIgnoreCase(countryCode).get();

		return productList == null ? Collections.emptyList() : productList.getProducts();
	}

	private List<Product> getNonIndiaProducts(String[] countryCodes) {

		Query<HkSgProductList> query = morphia.getDatastore()
				.createQuery(HkSgProductList.class);
		
		CriteriaContainer[] criteria = new CriteriaContainerImpl[countryCodes.length];
		
		for (int i = 0; i < countryCodes.length; i++) {
			criteria[i] = query.criteria("countryCode").equalIgnoreCase(countryCodes[i]);
		}

		query.or(criteria);
		HkSgProductList productList = query.get();

		return productList == null ? Collections.emptyList() : productList.getProducts();
	}

	private void sort(List<BaseProduct> baseProducts) {

		for (BaseProduct baseProduct : baseProducts) {
			if (!baseProduct.getVendors().isEmpty()) {
                baseProduct.getVendors()
						.sort(Comparator.comparing(Vendor::getVendorName, String.CASE_INSENSITIVE_ORDER));
			}
		}
        baseProducts.sort(Comparator.comparing(BaseProduct::getDescription, String.CASE_INSENSITIVE_ORDER));
	}

}
