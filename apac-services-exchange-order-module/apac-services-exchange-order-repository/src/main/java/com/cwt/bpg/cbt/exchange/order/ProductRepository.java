package com.cwt.bpg.cbt.exchange.order;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;
import org.mongodb.morphia.query.UpdateResults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cwt.bpg.cbt.calculator.model.Country;
import com.cwt.bpg.cbt.exchange.order.model.BaseProduct;
import com.cwt.bpg.cbt.exchange.order.model.HkSgProductList;
import com.cwt.bpg.cbt.exchange.order.model.InProductList;
import com.cwt.bpg.cbt.exchange.order.model.IndiaProduct;
import com.cwt.bpg.cbt.exchange.order.model.Product;
import com.cwt.bpg.cbt.exchange.order.model.Vendor;
import com.cwt.bpg.cbt.mongodb.config.MorphiaComponent;
import com.mongodb.BasicDBObject;

@Repository
public class ProductRepository<T> implements ProductDao {

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
            return updateResults.getWriteResult().getN() > 0 ? product.getProductCode() : NO_RESULT;
        }
        else {
            Datastore datastore = morphia.getDatastore();
            UpdateResults updateResults = datastore.update(
                    datastore.createQuery(HkSgProductList.class)
                            .filter(COUNTRY_CODE, countryCode),
                    datastore.createUpdateOperations(HkSgProductList.class)
                            .push(PRODUCTS, product)
            );
            return updateResults.getWriteResult().getN() > 0 ? product.getProductCode() : NO_RESULT;
        }
    }

    //TODO: remove duplicate code
    private String updateProduct(String countryCode, BaseProduct product) {
        if (Country.INDIA.getCode().equalsIgnoreCase(countryCode)) {
            Datastore datastore = morphia.getDatastore();
            UpdateResults updateResults = datastore.update(
                    datastore.createQuery(InProductList.class)
                            .filter(COUNTRY_CODE, countryCode)
                            .filter(PRODUCTS_PRODUCTCODE, product.getProductCode()),
                    datastore.createUpdateOperations(InProductList.class)
                            .set("products.$", product)
            );
            return updateResults.getWriteResult().getN() > 0 ? product.getProductCode() : NO_RESULT;
        }
        else {
            Datastore datastore = morphia.getDatastore();
            UpdateResults updateResults = datastore.update(
                    datastore.createQuery(HkSgProductList.class)
                            .filter(COUNTRY_CODE, countryCode)
                            .filter(PRODUCTS_PRODUCTCODE, product.getProductCode()),
                    datastore.createUpdateOperations(HkSgProductList.class)
                            .set("products.$", product)
            );
            return updateResults.getWriteResult().getN() > 0 ? product.getProductCode() : NO_RESULT;
        }
    }

    public String saveVendor(String countryCode, String productCode, Vendor vendor, boolean insertFlag) {
        if (insertFlag) {
            return insertVendor(countryCode, productCode, vendor);
        }
        else {
            return updateVendor(countryCode, vendor);
        }

    }

    private String insertVendor(String countryCode, String productCode, Vendor vendor) {
        if (Country.INDIA.getCode().equalsIgnoreCase(countryCode)) {
            Datastore datastore = morphia.getDatastore();
            UpdateResults updateResults = datastore.update(
                    datastore.createQuery(InProductList.class)
                            .filter(COUNTRY_CODE, countryCode)
                            .filter(PRODUCTS_PRODUCTCODE, productCode),
                    datastore.createUpdateOperations(InProductList.class)
                            .push(PRODUCTS_DOLLAR_VENDORS, vendor)
            );
            return updateResults.getWriteResult().getN() > 0 ? vendor.getCode() : NO_RESULT;
        }
        else {
            Datastore datastore = morphia.getDatastore();
            UpdateResults updateResults = datastore.update(
                    datastore.createQuery(HkSgProductList.class)
                            .filter(COUNTRY_CODE, countryCode)
                            .filter(PRODUCTS_PRODUCTCODE, productCode),
                    datastore.createUpdateOperations(HkSgProductList.class)
                            .push(PRODUCTS_DOLLAR_VENDORS, vendor)
            );
            return updateResults.getWriteResult().getN() > 0 ? vendor.getCode() : NO_RESULT;
        }
    }

    private String updateVendor(String countryCode, Vendor vendor) {
        if (Country.INDIA.getCode().equalsIgnoreCase(countryCode)) {
            Datastore datastore = morphia.getDatastore();
            Query<InProductList> query = datastore.createQuery(InProductList.class)
                    .filter(COUNTRY_CODE, countryCode)
                    .filter(PRODUCTS_VENDORS_CODE, vendor.getCode());

            List<String> productsToUpdate = new ArrayList<>();
            List<InProductList> productLists = query.asList();

            productLists.forEach(productList -> productList.getProducts().forEach(product -> {
                if (product.getVendors().stream().anyMatch(v -> vendor.getCode().equals(v.getCode()))) {
                    productsToUpdate.add(product.getProductCode());
                }
            }));

            removeVendor(countryCode, vendor.getCode());
            productsToUpdate.forEach(productCode -> insertVendor(countryCode, productCode, vendor));

            return vendor.getCode();
        }
        else {
            Datastore datastore = morphia.getDatastore();
            Query<HkSgProductList> query = datastore.createQuery(HkSgProductList.class)
                    .filter(COUNTRY_CODE, countryCode)
                    .filter(PRODUCTS_VENDORS_CODE, vendor.getCode());

            List<String> productsToUpdate = new ArrayList<>();
            List<HkSgProductList> productLists = query.asList();

            productLists.forEach(productList -> productList.getProducts().forEach(product -> {
                if (product.getVendors().stream().anyMatch(v -> vendor.getCode().equals(v.getCode()))) {
                    productsToUpdate.add(product.getProductCode());
                }
            }));

            removeVendor(countryCode, vendor.getCode());
            productsToUpdate.forEach(productCode -> insertVendor(countryCode, productCode, vendor));

            return vendor.getCode();
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

		if (Country.INDIA.getCode().equalsIgnoreCase(countryCode)) {
			return InProductList.class;
		}
		return HkSgProductList.class;
	}
}
