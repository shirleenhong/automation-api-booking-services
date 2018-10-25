package com.cwt.bpg.cbt.exchange.order;

import java.util.*;

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
public class ProductRepository {

    @Autowired
    private MorphiaComponent morphia;

    private static final String COUNTRY_CODE = "countryCode";
    private static final String PRODUCTS = "products";
    private static final String PRODUCT_CODE = "productCode";
    private static final String PRODUCTS_PRODUCT_CODE = "products.productCode";
    
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

    public String saveProduct(String countryCode, BaseProduct product, boolean insertFlag) {
        if (insertFlag) {
            return insertProduct(countryCode, product);
        }
        else {
            return updateProduct(countryCode, product);
        }
    }

    private String insertProduct(String countryCode, BaseProduct product) {
        if (Country.INDIA.getCode().equalsIgnoreCase(countryCode)) {
            Datastore datastore = morphia.getDatastore();
            UpdateResults updateResults = datastore.update(
                    datastore.createQuery(InProductList.class)
                            .filter(COUNTRY_CODE, countryCode),
                    datastore.createUpdateOperations(InProductList.class)
                            .push(PRODUCTS, product)
            );
            return String.valueOf(updateResults.getWriteResult().getN());

        }
        else {
            Datastore datastore = morphia.getDatastore();
            UpdateResults updateResults = datastore.update(
                    datastore.createQuery(HkSgProductList.class)
                            .filter(COUNTRY_CODE, countryCode),
                    datastore.createUpdateOperations(HkSgProductList.class)
                            .push(PRODUCTS, product)
            );
            return String.valueOf(updateResults.getWriteResult().getN());
        }
    }

    //TODO: remove duplicate code
    private String updateProduct(String countryCode, BaseProduct product) {
        if (Country.INDIA.getCode().equalsIgnoreCase(countryCode)) {
            Datastore datastore = morphia.getDatastore();
            UpdateResults updateResults = datastore.update(
                    datastore.createQuery(InProductList.class)
                            .filter(COUNTRY_CODE, countryCode)
                            .filter(PRODUCTS_PRODUCT_CODE, product.getProductCode()),
                    datastore.createUpdateOperations(InProductList.class)
                            .set("products.$", product)
            );
            return String.valueOf(updateResults.getWriteResult().getN());
        }
        else {
            Datastore datastore = morphia.getDatastore();
            UpdateResults updateResults = datastore.update(
                    datastore.createQuery(HkSgProductList.class)
                            .filter(COUNTRY_CODE, countryCode)
                            .filter(PRODUCTS_PRODUCT_CODE, product.getProductCode()),
                    datastore.createUpdateOperations(HkSgProductList.class)
                            .set("products.$", product)
            );
            return String.valueOf(updateResults.getWriteResult().getN());
        }
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

    
    public String removeProduct(String countryCode, String productCode) {
    	Datastore datastore = morphia.getDatastore();
    	String noResult = "";
    	
    	if (Country.INDIA.getCode().equalsIgnoreCase(countryCode)) {
    		final Query<InProductList> updateQuery = datastore
					.createQuery(InProductList.class)
					.filter(COUNTRY_CODE, countryCode)
					.filter(PRODUCTS_PRODUCT_CODE, productCode);
			
			UpdateOperations<InProductList> ops = datastore
					.createUpdateOperations(InProductList.class).disableValidation()
					.removeAll(PRODUCTS, new BasicDBObject(PRODUCT_CODE, productCode));
			
			UpdateResults updateResults = datastore.update(updateQuery, ops);

			return updateResults.getWriteResult().getN() > 0 ? productCode : noResult;
    	}
    	else {
			final Query<HkSgProductList> updateQuery = datastore
					.createQuery(HkSgProductList.class)
					.filter(COUNTRY_CODE, countryCode)
					.filter(PRODUCTS_PRODUCT_CODE, productCode);
			
			UpdateOperations<HkSgProductList> ops = datastore
					.createUpdateOperations(HkSgProductList.class).disableValidation()
					.removeAll(PRODUCTS, new BasicDBObject(PRODUCT_CODE, productCode));
			
			UpdateResults updateResults = datastore.update(updateQuery, ops);

			return updateResults.getWriteResult().getN() > 0 ? productCode : noResult;
    	}
    }
    
    
    public String removeVendor(String countryCode, String vendorCode) {
    	Datastore datastore = morphia.getDatastore();
    	String noResult = "";
    	int results = 0;
    	
    	if (Country.INDIA.getCode().equalsIgnoreCase(countryCode)) {
    		Query<InProductList> updateQuery = datastore
					.createQuery(InProductList.class)
					.filter(COUNTRY_CODE, countryCode);
    		
    		List<InProductList> productList = updateQuery.asList();

			
			for (IndiaProduct product : productList.get(0).getProducts()) {
				updateQuery = datastore.createQuery(InProductList.class)
						.filter(COUNTRY_CODE, countryCode)
						.filter(PRODUCTS_PRODUCT_CODE, product.getProductCode());
				
				UpdateOperations<InProductList> ops = datastore
						.createUpdateOperations(InProductList.class).disableValidation()
						.removeAll("products.$.vendors", new BasicDBObject("code", vendorCode));
				
				UpdateResults updateResults = datastore.update(updateQuery, ops);
				results += updateResults.getWriteResult().getN();
			}

			return results > 0 ? vendorCode : noResult;
    	}
    	else {
    		Query<HkSgProductList> updateQuery = datastore
					.createQuery(HkSgProductList.class)
					.filter(COUNTRY_CODE, countryCode);
    		
    		List<HkSgProductList> productList = updateQuery.asList();

			for (Product product : productList.get(0).getProducts()) {
				updateQuery = datastore.createQuery(HkSgProductList.class)
						.filter(COUNTRY_CODE, countryCode)
						.filter(PRODUCTS_PRODUCT_CODE, product.getProductCode());
				
				UpdateOperations<HkSgProductList> ops = datastore
						.createUpdateOperations(HkSgProductList.class).disableValidation()
						.removeAll("products.$.vendors", new BasicDBObject("code", vendorCode));
				
				UpdateResults updateResults = datastore.update(updateQuery, ops);
				results += updateResults.getWriteResult().getN();
			}

			return results > 0 ? vendorCode : noResult;
    	}
    }
}
