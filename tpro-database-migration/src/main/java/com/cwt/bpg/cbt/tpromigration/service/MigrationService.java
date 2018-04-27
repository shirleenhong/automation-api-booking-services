package com.cwt.bpg.cbt.tpromigration.service;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.cwt.bpg.cbt.tpromigration.mongodb.config.MongoDbConnection;
import com.cwt.bpg.cbt.tpromigration.mongodb.mapper.DBObjectMapper;
import com.cwt.bpg.cbt.tpromigration.mssqldb.dao.ClientMerchantFeeDAO;
import com.cwt.bpg.cbt.tpromigration.mssqldb.dao.CurrencyDAO;
import com.cwt.bpg.cbt.tpromigration.mssqldb.dao.ProductCodeDAO;
import com.cwt.bpg.cbt.tpromigration.mssqldb.dao.VendorDAO;
import com.cwt.bpg.cbt.tpromigration.mssqldb.model.ClientMerchantFee;
import com.cwt.bpg.cbt.tpromigration.mssqldb.model.Currency;
import com.cwt.bpg.cbt.tpromigration.mssqldb.model.Product;
import com.cwt.bpg.cbt.tpromigration.mssqldb.model.ProductList;
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
    @Autowired
    private ClientMerchantFeeDAO clientMerchantFeeDAO;
    @Autowired
    private CurrencyDAO currencyDAO;

    @Value("${com.cwt.tpromigration.mongodb.dbuser}")
    private String dbUser;

    @Value("${com.cwt.tpromigration.mongodb.dbpwd}")
    private String dbPwd;

    @Value("${com.cwt.tpromigration.mongodb.dbname}")
    private String dbName;
    
	@SuppressWarnings("unchecked")
	public void startMigration() throws JsonProcessingException {
		
		logger.info("start migration...");
		
		List<Vendor> vendorList = vendorDAO.listVendors();
		List<Product> products = productCodeDAO.listProductCodes();
		
		String countryCode = System.getProperty("spring.profiles.default");
		
		ProductList productList = new ProductList();
		
		
		for(Product product: products) {
			product.setCountryCode(countryCode);
			for(Vendor vendor: vendorList) {
				vendor.setCountryCode(countryCode);
				logger.info("product:"+product);
				logger.info("vendor:"+vendor);
				logger.info("vendor.getProductCodes():"+vendor.getProductCodes());
				if(vendor.getProductCodes() != null && vendor.getProductCodes().contains(product.getProductCode())) {
					product.getVendors().add(vendor);
				}
			}
		}
		productList.setProducts(products);
		
		productList.setCountryCode(countryCode);
		
		mongoDbConnection.getCollection("productList").insertOne(dBObjectMapper.mapAsDbDocument(productList));
	}
	
	
	@SuppressWarnings("unchecked")
	public void migrateMerchantFees() throws JsonProcessingException {
		
		logger.info("started merchant fee migration...");
		
		List<ClientMerchantFee> merchantFees = clientMerchantFeeDAO.listMerchantFees();
		
	
		for(ClientMerchantFee merchantFee: merchantFees) {
			mongoDbConnection.getCollection("clientMerchantFee").insertOne(dBObjectMapper.mapAsDbDocument(merchantFee));
		}
		
		logger.info("end of merchant fee migration...");
		
	}
	
	@SuppressWarnings("unchecked")
	public void migrateCurrencies() throws JsonProcessingException {
		
		logger.info("started currency migration...");
		List<Currency> currencies = currencyDAO.listCurrencies();
		List<Document> currDocs = new ArrayList<Document>();
		for(Currency currency:currencies) {
			currDocs.add(dBObjectMapper.mapAsDbDocument(currency));
		}
		
		mongoDbConnection.getCollection("currency").insertMany(currDocs);
		
		logger.info("end of currency migration...");
		
	}
	
	
	
}
