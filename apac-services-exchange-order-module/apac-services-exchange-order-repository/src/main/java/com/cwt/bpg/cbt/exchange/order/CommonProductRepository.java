package com.cwt.bpg.cbt.exchange.order;

import java.util.List;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;
import org.mongodb.morphia.query.UpdateResults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cwt.bpg.cbt.calculator.model.Country;
import com.cwt.bpg.cbt.exchange.order.model.HkSgProductList;
import com.cwt.bpg.cbt.exchange.order.model.InProductList;
import com.cwt.bpg.cbt.exchange.order.model.IndiaProduct;
import com.cwt.bpg.cbt.exchange.order.model.Product;
import com.cwt.bpg.cbt.mongodb.config.MorphiaComponent;
import com.mongodb.BasicDBObject;

@Repository
public class CommonProductRepository<T> implements ProductDao {

    @Autowired
    private MorphiaComponent morphia;

    private static final String COUNTRY_CODE = "countryCode";
    private static final String PRODUCTS = "products";
    private static final String PRODUCT_CODE = "productCode";
    private static final String VENDOR_CODE = "code";
    private static final String PRODUCTS_PRODUCTCODE = "products.productCode";
    private static final String PRODUCTS_VENDORS_CODE = "products.vendors.code";
    private static final String PRODUCTS_DOLLAR_VENDORS = "products.$.vendors";
    private static final String NO_RESULT = "";

    
	public String removeProduct(String countryCode, String productCode) {

		int results = 0;
		results = removeQuery(countryCode, null, results, getCurrentClass(countryCode),
				productCode, true);
		return results > 0 ? productCode : NO_RESULT;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String removeVendor(String countryCode, String vendorCode) {

		Class className = getCurrentClass(countryCode);
		Datastore datastore = morphia.getDatastore();
		
		Query<T> updateQuery = datastore
		        .createQuery(className)
		        .filter(COUNTRY_CODE, countryCode);

		List<T> queryList = updateQuery.asList();
		List<IndiaProduct> indiaProductList;
		List<Product> hkSgProductList;
		int results = 0;
		
		for(Object obj : queryList) {
			if(obj instanceof InProductList) {
				InProductList indiaProduct = (InProductList) obj;
				indiaProductList= indiaProduct.getProducts();
				
				for (IndiaProduct product : indiaProductList) {
				    results = removeQuery(countryCode, vendorCode, results, className,
				    		product.getProductCode(), false);
				}
			}else {
				HkSgProductList productList = (HkSgProductList) obj;
				hkSgProductList = productList.getProducts();
				
				for (Product product : hkSgProductList) {
				    results = removeQuery(countryCode, vendorCode, results, className,
				    		product.getProductCode(), false);
				}
			}
		}
		return results > 0 ? vendorCode : NO_RESULT;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private int removeQuery(String countryCode, String vendorCode,
			int results, Class className, String productCode, boolean isProductLevel) {
		
		Datastore datastore = morphia.getDatastore();
		
		Query<T> updateQuery = datastore.createQuery(className)
		        .filter(COUNTRY_CODE, countryCode)
		        .filter(PRODUCTS_PRODUCTCODE,  productCode);

		UpdateOperations<T> ops = datastore.createUpdateOperations(className)
				.disableValidation()
				.removeAll(isProductLevel ? PRODUCTS : PRODUCTS_DOLLAR_VENDORS,
						new BasicDBObject(isProductLevel ? PRODUCT_CODE : VENDOR_CODE,
								isProductLevel ? productCode : vendorCode));

		UpdateResults updateResults = datastore.update(updateQuery, ops);
		results += updateResults.getWriteResult().getN();
		return results;
	}	

	@SuppressWarnings("rawtypes")
	private Class getCurrentClass(String countryCode) {
		Class currentClass;
		if(Country.INDIA.getCode().equalsIgnoreCase(countryCode)){
			currentClass = InProductList.class;
		}else {
			currentClass = HkSgProductList.class;
		}
		return currentClass;
	}

}
