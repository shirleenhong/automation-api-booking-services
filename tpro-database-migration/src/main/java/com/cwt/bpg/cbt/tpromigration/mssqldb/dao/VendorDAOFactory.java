package com.cwt.bpg.cbt.tpromigration.mssqldb.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class VendorDAOFactory {

    @Autowired
    private VendorDAOImpl vendorDao;

    @Autowired
    private IndiaVendorDAOImpl indiaVendorDAO;

    public VendorDAO getVendorDAO(String countryCode){

        if(countryCode.equalsIgnoreCase("IN")){
            return indiaVendorDAO;
        }

        return vendorDao;
    }

}
