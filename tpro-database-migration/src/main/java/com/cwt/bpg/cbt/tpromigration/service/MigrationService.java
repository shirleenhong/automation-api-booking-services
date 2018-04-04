package com.cwt.bpg.cbt.tpromigration.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.cwt.bpg.cbt.tpromigration.mssqldb.dao.ProductCodeDAO;
import com.cwt.bpg.cbt.tpromigration.mssqldb.dao.VendorDAO;

@Service
public class MigrationService {

    @Autowired
    private VendorDAO vendorDAO;
    @Autowired
    private ProductCodeDAO productCodeDAO;

    @Value("${com.cwt.tpromigration.mongodb.dbuser}")
    private String dbUser;

    @Value("${com.cwt.tpromigration.mongodb.dbpwd}")
    private String dbPwd;

    @Value("${com.cwt.tpromigration.mongodb.dbname}")
    private String dbName;
    
	public void startMigration() {
		


		System.out.println("start migration...");
		vendorDAO.listVendors();
		productCodeDAO.listProductCodes();
	}
}
