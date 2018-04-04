package com.cwt.bpg.cbt.tpromigration.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.cwt.bpg.cbt.tpromigration.mongodb.config.MongoDbConnection;
import com.cwt.bpg.cbt.tpromigration.mongodb.mapper.DBObjectMapper;
import com.cwt.bpg.cbt.tpromigration.mssqldb.dao.ProductCodeDAO;
import com.cwt.bpg.cbt.tpromigration.mssqldb.dao.VendorDAO;
import com.cwt.bpg.cbt.tpromigration.mssqldb.model.ProductCode;
import com.cwt.bpg.cbt.tpromigration.mssqldb.model.Vendor;
import com.fasterxml.jackson.core.JsonProcessingException;

@Service
public class MigrationService {

	private static final Logger logger = LoggerFactory.getLogger(MigrationService.class);
	@Autowired
	private MongoDbConnection mongoDbConnection;
	@Autowired
	private DBObjectMapper dBObjectMapper;
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
    
	public void startMigration() throws JsonProcessingException {
		logger.info("start migration...");
		String countryCode = System.getProperty("spring.profiles.default");
		for(ProductCode productCode: productCodeDAO.listProductCodes()) {
			productCode.setCountryCode(countryCode);
			mongoDbConnection.getCollection(ProductCode.COLLECTION).insertOne(dBObjectMapper.mapAsDbDocument(productCode));
		}
		for(Vendor vendor: vendorDAO.listVendors()) {
			vendor.setCountryCode(countryCode);
			mongoDbConnection.getCollection(Vendor.COLLECTION).insertOne(dBObjectMapper.mapAsDbDocument(vendor));
		}
		logger.info("done migration...");
	}
}
