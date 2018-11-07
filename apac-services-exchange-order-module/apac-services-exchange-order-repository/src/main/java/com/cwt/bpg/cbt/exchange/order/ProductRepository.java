package com.cwt.bpg.cbt.exchange.order;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;
import org.mongodb.morphia.query.UpdateResults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cwt.bpg.cbt.calculator.model.Country;
import com.cwt.bpg.cbt.exchange.order.model.*;
import com.cwt.bpg.cbt.mongodb.config.MorphiaComponent;
import com.mongodb.BasicDBObject;

@Repository
public class ProductRepository<T extends ProductList> implements ProductDao {

    @Autowired
    private MorphiaComponent morphia;

    private static final String COUNTRY_CODE = "countryCode";
    private static final String PRODUCTS = "products";
    private static final String PRODUCT_CODE = "productCode";
    private static final String VENDOR_CODE = "code";
    private static final String PRODUCTS_PRODUCTCODE = "products.productCode";
    private static final String PRODUCTS_VENDORS_CODE = "products.vendors.code";
    private static final String PRODUCTS_DOLLAR_VENDORS = "products.$.vendors";
    private static final String PRODUCTS_DOLLAR = "products.$";
    private static final String NO_RESULT = "";

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
            return updateProduct(countryCode, product);
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

		UpdateResults updateResults = datastore.update(query, updateOperations);

		return updateResults.getWriteResult().getN() > 0 ? product.getProductCode()
				: NO_RESULT;
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

		UpdateResults updateResults = datastore.update(query, updateOperations);

		return updateResults.getWriteResult().getN() > 0 ? product.getProductCode()
				: NO_RESULT;
	}

	public String saveVendor(String countryCode, String productCode, Vendor vendor, boolean insertFlag) {
		if (insertFlag) {
			return insertVendor(countryCode, productCode, vendor);
		}
		else {
			return updateVendor(countryCode, vendor);
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

		UpdateResults updateResults = datastore.update(query, updateOperations);

		return updateResults.getWriteResult().getN() > 0 ? vendor.getCode() : NO_RESULT;
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

		int results = 0;
		results += removeQuery(countryCode, null, productCode);
		return results > 0 ? productCode : NO_RESULT;
	}

	@SuppressWarnings({ "unchecked" })
	public String removeVendor(String countryCode, String vendorCode) {

		Datastore datastore = morphia.getDatastore();

		Query<T> updateQuery = datastore.createQuery(getCurrentClass(countryCode))
				.filter(COUNTRY_CODE, countryCode);

		List<T> queryList = updateQuery.asList();
		int results = 0;

		for (T obj : queryList) {
			for (Object product : obj.getProducts()) {
				results += removeQuery(countryCode, vendorCode,
						((BaseProduct) product).getProductCode());
			}
		}
		return results > 0 ? vendorCode : NO_RESULT;
	}

	@SuppressWarnings({ "unchecked" })
	private int removeQuery(String countryCode, String vendorCode, String productCode) {

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

		UpdateResults updateResults = datastore.update(updateQuery, ops);
		return updateResults.getWriteResult().getN();
	}

	@SuppressWarnings("rawtypes")
	private Class getCurrentClass(String countryCode) {
		return Country.INDIA.getCode().equalsIgnoreCase(countryCode) ? InProductList.class
				: HkSgProductList.class;
	}
}
