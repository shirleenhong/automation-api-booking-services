package com.cwt.bpg.cbt.tpromigration.mssqldb.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductDAOFactory {

    @Autowired
    private ProductCodeDAOImpl productDAO;

    @Autowired
    private IndiaProductCodeDAOImpl indiaProductCodeDAO;

    @SuppressWarnings("rawtypes")
    public ProductCodeDAO getProductCodeDAO(String countryCode) {

        if (countryCode.equalsIgnoreCase("IN")) {
            return indiaProductCodeDAO;
        }

        return productDAO;
    }
}
