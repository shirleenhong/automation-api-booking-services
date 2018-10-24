package com.cwt.bpg.cbt.exchange.order;

import java.util.*;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.UpdateResults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cwt.bpg.cbt.calculator.model.Country;
import com.cwt.bpg.cbt.exchange.order.model.*;
import com.cwt.bpg.cbt.mongodb.config.MorphiaComponent;

@Repository
public class ProductRepository {

    @Autowired
    private MorphiaComponent morphia;


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
                .field("countryCode").equalIgnoreCase(countryCode).get();

        return productList == null ? Collections.emptyList() : productList.getProducts();
    }

    private List<IndiaProduct> getIndiaProducts(String countryCode) {
        InProductList productList = morphia.getDatastore().createQuery(InProductList.class)
                .field("countryCode").equalIgnoreCase(countryCode).get();

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
                            .filter("countryCode", countryCode),
                    datastore.createUpdateOperations(InProductList.class)
                            .push("products", product)
            );
            return String.valueOf(updateResults.getWriteResult().getN());

        }
        else {
            Datastore datastore = morphia.getDatastore();
            UpdateResults updateResults = datastore.update(
                    datastore.createQuery(HkSgProductList.class)
                            .filter("countryCode", countryCode),
                    datastore.createUpdateOperations(HkSgProductList.class)
                            .push("products", product)
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
                            .filter("countryCode", countryCode)
                            .filter("products.productCode", product.getProductCode()),
                    datastore.createUpdateOperations(InProductList.class)
                            .set("products.$", product)
            );
            return String.valueOf(updateResults.getWriteResult().getN());
        }
        else {
            Datastore datastore = morphia.getDatastore();
            UpdateResults updateResults = datastore.update(
                    datastore.createQuery(HkSgProductList.class)
                            .filter("countryCode", countryCode)
                            .filter("products.productCode", product.getProductCode()),
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

}
