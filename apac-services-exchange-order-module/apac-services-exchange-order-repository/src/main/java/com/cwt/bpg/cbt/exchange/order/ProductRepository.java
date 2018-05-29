package com.cwt.bpg.cbt.exchange.order;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cwt.bpg.cbt.exchange.order.model.*;
import com.cwt.bpg.cbt.mongodb.config.MorphiaComponent;

@Repository
public class ProductRepository
{

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductRepository.class);
    private static final String INDIA_COUNTRY_CODE = "IN";

    @Autowired
	private MorphiaComponent morphia;

	public List<Product> getProducts(String countryCode) {

		List<Product> products = new ArrayList<>();
		try {
			if (INDIA_COUNTRY_CODE.equalsIgnoreCase(countryCode)) {
				products.addAll(getInProducts(countryCode));
			}
			else {
				products.addAll(getHkSgProducts(countryCode));
			}
			sort(products);
		}
		catch (Exception e) {
			LOGGER.error("Unable to parse product list for {} {}", countryCode, e.getMessage());
		}

		return products;
	}

	private List<HkSgProduct> getHkSgProducts(String countryCode) {
		HkSgProductList productList = morphia.getDatastore().createQuery(HkSgProductList.class)
				.field("countryCode").equal(countryCode).get();

		return productList == null ? Collections.<HkSgProduct>emptyList() : productList.getProducts();
	}

	private List<InProduct> getInProducts(String countryCode) {
		InProductList productList = morphia.getDatastore().createQuery(InProductList.class)
				.field("countryCode").equal(countryCode).get();

		return productList == null ? Collections.<InProduct>emptyList() : productList.getProducts();
	}

    private void sort(List<Product> products)
    {
        if (products != null && !products.isEmpty())
        {
            for (Product product : products)
            {
                if (product.getVendors() != null && !product.getVendors().isEmpty())
                {
                    product.getVendors().sort(Comparator.comparing(Vendor::getVendorName, String.CASE_INSENSITIVE_ORDER));
                }
            }
            products.sort(Comparator.comparing(Product::getDescription, String.CASE_INSENSITIVE_ORDER));
        }
    }

}
