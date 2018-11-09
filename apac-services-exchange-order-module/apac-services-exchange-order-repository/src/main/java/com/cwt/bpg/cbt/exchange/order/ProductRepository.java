package com.cwt.bpg.cbt.exchange.order;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cwt.bpg.cbt.calculator.model.Country;
import com.cwt.bpg.cbt.exchange.order.model.*;
import com.cwt.bpg.cbt.mongodb.config.MorphiaComponent;
import com.mongodb.BasicDBObject;

@Repository
public class ProductRepository<T extends ProductList> {

    @Autowired
    private MorphiaComponent morphia;

    static final String COUNTRY_CODE = "countryCode";
    static final String PRODUCTS = "products";
    static final String PRODUCT_CODE = "productCode";
    static final String VENDOR_CODE = "code";
    static final String PRODUCTS_PRODUCTCODE = "products.productCode";
    static final String PRODUCTS_VENDORS_CODE = "products.vendors.code";
    static final String PRODUCTS_DOLLAR_VENDORS = "products.$.vendors";
    static final String PRODUCTS_DOLLAR = "products.$";
    static final String NO_RESULT = "";

    public List<BaseProduct> getProducts(String countryCode) {

        List<BaseProduct> baseProducts = new ArrayList<>();

        if (Country.INDIA.getCode().equalsIgnoreCase(countryCode)) {
            baseProducts.addAll(getIndiaProducts(countryCode));
        }
        else {
            baseProducts.addAll(getNonIndiaProducts(countryCode));
        }
        sort(baseProducts);

        return baseProducts;
    }

    private List<Product> getNonIndiaProducts(String countryCode) {
        HkSgProductList productList = morphia.getDatastore().createQuery(HkSgProductList.class)
                .field(COUNTRY_CODE).equalIgnoreCase(countryCode).get();

        return productList == null ? Collections.emptyList() : productList.getProducts();
    }

    private List<IndiaProduct> getIndiaProducts(String countryCode) {
        InProductList productList = morphia.getDatastore().createQuery(InProductList.class)
                .field(COUNTRY_CODE).equalIgnoreCase(countryCode).get();

        return productList == null ? Collections.emptyList() : productList.getProducts();
    }

    private void sort(List<BaseProduct> baseProducts) {

        for (BaseProduct baseProduct : baseProducts) {
            if (!baseProduct.getVendors().isEmpty()) {
                baseProduct.getVendors()
                        .sort(Comparator.comparing(Vendor::getName, String.CASE_INSENSITIVE_ORDER));
            }
        }
        baseProducts.sort(Comparator.comparing(BaseProduct::getDescription, String.CASE_INSENSITIVE_ORDER));
    }
    
    public String saveProduct(String countryCode, BaseProduct product, boolean insertFlag) {
        if (insertFlag) {
            return insertProduct(countryCode, product);
        }
		else {
			return checkIfExists(countryCode, product.getProductCode(), null) ? NO_RESULT
					: updateProduct(countryCode, product);
		}
    }

	@SuppressWarnings("unchecked")
	private String insertProduct(String countryCode, BaseProduct product) {
		Datastore datastore = morphia.getDatastore();
		
		Query<T> query = datastore.createQuery(getCurrentClass(countryCode))
				.filter(COUNTRY_CODE, countryCode);
		UpdateOperations<T> updateOperations = datastore
				.createUpdateOperations(getCurrentClass(countryCode))
				.push(PRODUCTS, product);

		datastore.update(query, updateOperations);

		return product.getProductCode();
	}

	@SuppressWarnings("unchecked")
	private String updateProduct(String countryCode, BaseProduct product) {
		Datastore datastore = morphia.getDatastore();

		Query<T> query = datastore.createQuery(getCurrentClass(countryCode))
				.filter(COUNTRY_CODE, countryCode)
				.filter(PRODUCTS_PRODUCTCODE, product.getProductCode());

		UpdateOperations<T> updateOperations = datastore
				.createUpdateOperations(getCurrentClass(countryCode))
				.set(PRODUCTS_DOLLAR, product);

		datastore.update(query, updateOperations);

		return product.getProductCode();
	}

	public String saveVendor(String countryCode, String productCode, Vendor vendor, boolean insertFlag) {
		if (insertFlag) {
			return insertVendor(countryCode, productCode, vendor);
		}
		else {
			return checkIfExists(countryCode, null, vendor.getCode()) ? NO_RESULT
					: updateVendor(countryCode, vendor);
		}
	}

	@SuppressWarnings("unchecked")
	private String insertVendor(String countryCode, String productCode, Vendor vendor) {
		Datastore datastore = morphia.getDatastore();
		
		Query<T> query = datastore.createQuery(getCurrentClass(countryCode))
				.filter(COUNTRY_CODE, countryCode)
				.filter(PRODUCTS_PRODUCTCODE, productCode);
		UpdateOperations<T> updateOperations = datastore
				.createUpdateOperations(getCurrentClass(countryCode))
				.push(PRODUCTS_DOLLAR_VENDORS, vendor);

		datastore.update(query, updateOperations);

		return vendor.getCode();
	}

    @SuppressWarnings("unchecked")
	private String updateVendor(String countryCode, Vendor vendor) {
    	Datastore datastore = morphia.getDatastore();
    	
        Query<T> query = datastore.createQuery(getCurrentClass(countryCode))
                .filter(COUNTRY_CODE, countryCode)
                .filter(PRODUCTS_VENDORS_CODE, vendor.getCode());

        List<String> productsToUpdate = new ArrayList<>();
        List<T> productLists = query.asList();
		for (T obj : productLists) {
			for (Object product : obj.getProducts()) {
				if (((BaseProduct) product).getVendors().stream()
						.anyMatch(v -> vendor.getCode().equals(v.getCode()))) {
					productsToUpdate.add(((BaseProduct) product).getProductCode());
				}
			}
		}
		
        removeVendor(countryCode, vendor.getCode());
        productsToUpdate.forEach(productCode -> insertVendor(countryCode, productCode, vendor));

        return vendor.getCode();
    }

	public String removeProduct(String countryCode, String productCode) {

		if(checkIfExists(countryCode, productCode, null)) {
			return NO_RESULT;
		}
		
		removeQuery(countryCode, null, productCode);
		return productCode;
	}

	@SuppressWarnings({ "unchecked" })
	public String removeVendor(String countryCode, String vendorCode) {

		if(checkIfExists(countryCode, null, vendorCode)) {
			return NO_RESULT;
		}
		
		Datastore datastore = morphia.getDatastore();

		Query<T> updateQuery = datastore.createQuery(getCurrentClass(countryCode))
				.filter(COUNTRY_CODE, countryCode);

		List<T> queryList = updateQuery.asList();

		for (T obj : queryList) {
			for (Object product : obj.getProducts()) {
				removeQuery(countryCode, vendorCode,
						((BaseProduct) product).getProductCode());
			}
		}
		return vendorCode;
	}

	@SuppressWarnings({ "unchecked" })
	private void removeQuery(String countryCode, String vendorCode, String productCode) {

		Datastore datastore = morphia.getDatastore();

		Query<T> updateQuery = datastore.createQuery(getCurrentClass(countryCode))
				.filter(COUNTRY_CODE, countryCode)
				.filter(PRODUCTS_PRODUCTCODE, productCode);

		boolean isProductLevel = StringUtils.isEmpty(vendorCode);
		UpdateOperations<T> ops = datastore
				.createUpdateOperations(getCurrentClass(countryCode)).disableValidation()
				.removeAll(isProductLevel ? PRODUCTS : PRODUCTS_DOLLAR_VENDORS,
						new BasicDBObject(isProductLevel ? PRODUCT_CODE : VENDOR_CODE,
								isProductLevel ? productCode : vendorCode));

		datastore.update(updateQuery, ops);
	}
	
	
	@SuppressWarnings("unchecked")
	private boolean checkIfExists(String countryCode, String productCode, String vendorCode) {
		
		Datastore datastore = morphia.getDatastore();

		boolean isProductLevel = StringUtils.isEmpty(vendorCode)
				&& StringUtils.isNotEmpty(productCode);
		
		Query<T> query = datastore.createQuery(getCurrentClass(countryCode))
				.filter(COUNTRY_CODE, countryCode)
				.filter(isProductLevel ? PRODUCTS_PRODUCTCODE : PRODUCTS_VENDORS_CODE,
						isProductLevel ? productCode : vendorCode);

		return query.asList().isEmpty();
	}

	@SuppressWarnings("rawtypes")
	private Class getCurrentClass(String countryCode) {
		return Country.INDIA.getCode().equalsIgnoreCase(countryCode) ? InProductList.class
				: HkSgProductList.class;
	}
}
