package com.cwt.bpg.cbt.tpromigration.mssqldb.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductDAOFactory {

    @Autowired
    private ProductCodeDAOImpl productDAO;

    @Autowired
    private IndiaProductCodeDAOImpl indiaProductCodeDAO;

    public ProductCodeDAO getProductCodeDAO() {

        if (System.getProperty("spring.profiles.default").trim().toLowerCase().equals("in")) {
            return indiaProductCodeDAO;
        }

        return productDAO;
    }
}
