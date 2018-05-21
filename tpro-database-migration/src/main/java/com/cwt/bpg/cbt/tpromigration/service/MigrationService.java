package com.cwt.bpg.cbt.tpromigration.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.cwt.bpg.cbt.tpromigration.mongodb.config.MongoDbConnection;
import com.cwt.bpg.cbt.tpromigration.mongodb.mapper.DBObjectMapper;
import com.cwt.bpg.cbt.tpromigration.mssqldb.dao.AirlineRuleDAOImpl;
import com.cwt.bpg.cbt.tpromigration.mssqldb.dao.ClientDAOImpl;
import com.cwt.bpg.cbt.tpromigration.mssqldb.dao.ClientMerchantFeeDAO;
import com.cwt.bpg.cbt.tpromigration.mssqldb.dao.CurrencyDAO;
import com.cwt.bpg.cbt.tpromigration.mssqldb.dao.ProductCodeDAO;
import com.cwt.bpg.cbt.tpromigration.mssqldb.dao.VendorDAO;
import com.cwt.bpg.cbt.tpromigration.mssqldb.model.AirlineRule;
import com.cwt.bpg.cbt.tpromigration.mssqldb.model.Bank;
import com.cwt.bpg.cbt.tpromigration.mssqldb.model.BankVendor;
import com.cwt.bpg.cbt.tpromigration.mssqldb.model.Client;
import com.cwt.bpg.cbt.tpromigration.mssqldb.model.ClientMerchantFee;
import com.cwt.bpg.cbt.tpromigration.mssqldb.model.Currency;
import com.cwt.bpg.cbt.tpromigration.mssqldb.model.Product;
import com.cwt.bpg.cbt.tpromigration.mssqldb.model.ProductList;
import com.cwt.bpg.cbt.tpromigration.mssqldb.model.ProductMerchantFee;
import com.cwt.bpg.cbt.tpromigration.mssqldb.model.Vendor;
import com.fasterxml.jackson.core.JsonProcessingException;

@Service
public class MigrationService {

	private static final Logger LOGGER = LoggerFactory.getLogger(MigrationService.class);

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

	@Autowired
	private AirlineRuleDAOImpl airlineRuleDAO;

	@Autowired
	private ClientDAOImpl clientDAO;

	@Value("${com.cwt.tpromigration.mongodb.dbuser}")
	private String dbUser;

	@Value("${com.cwt.tpromigration.mongodb.dbpwd}")
	private String dbPwd;

	@Value("${com.cwt.tpromigration.mongodb.dbname}")
	private String dbName;

	@SuppressWarnings("unchecked")
	public void startMigration() throws JsonProcessingException {

		LOGGER.info("start migration...");

		List<Vendor> vendorList = vendorDAO.listVendors();
		List<Product> products = productCodeDAO.listProductCodes();

		String countryCode = System.getProperty("spring.profiles.default");

		ProductList productList = new ProductList();

		for (Product product : products) {
			product.setCountryCode(countryCode);
			for (Vendor vendor : vendorList) {
				vendor.setCountryCode(countryCode);
				LOGGER.info("product:{}", product);
				LOGGER.info("vendor:{}", vendor);
				LOGGER.info("vendor.getProductCodes():" + vendor.getProductCodes());
				if (vendor.getProductCodes() != null
						&& vendor.getProductCodes().contains(product.getProductCode())) {
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

		LOGGER.info("Started merchant fee migration...");

		List<ClientMerchantFee> merchantFees = clientMerchantFeeDAO.listMerchantFees();

		for (ClientMerchantFee merchantFee : merchantFees) {
			mongoDbConnection.getCollection("clientMerchantFee")
					.insertOne(dBObjectMapper.mapAsDbDocument(merchantFee));
		}

		LOGGER.info("End of merchant fee migration...");

	}

	@SuppressWarnings("unchecked")
	public void migrateCurrencies() throws JsonProcessingException {

		LOGGER.info("Started currency migration...");
		List<Currency> currencies = currencyDAO.listCurrencies();
		List<Document> currDocs = new ArrayList<>();
		for (Currency currency : currencies) {
			currDocs.add(dBObjectMapper.mapAsDbDocument(currency));
		}

		mongoDbConnection.getCollection("currency").insertMany(currDocs);

		LOGGER.info("End of currency migration...");

	}

	@SuppressWarnings("unchecked")
	public void migrateAirlineRules() throws JsonProcessingException {

		LOGGER.info("Started airline rule migration...");
		List<AirlineRule> rules = airlineRuleDAO.list();
		List<Document> docs = new ArrayList<>();
		for (AirlineRule rule : rules) {
			docs.add(dBObjectMapper.mapAsDbDocument(rule.getCode(), rule));
		}

		mongoDbConnection.getCollection("airlineRules").insertMany(docs);

		LOGGER.info("End of airline rule migration...");

	}

	@SuppressWarnings("unchecked")
	public void migrateClients() throws JsonProcessingException {

		LOGGER.info("Started clients migration...");

		Map<Integer, List<ProductMerchantFee>> productsMap = getProductMap(clientDAO.getProducts());
		Map<Integer, List<BankVendor>> vendorsMap = getVendoMap(clientDAO.getVendors());
		Map<Integer, List<Bank>> banksMap = getBankMap(clientDAO.getBanks());
		List<Client> clients = clientDAO.getClients();

		clients.addAll(updateClients(clients, productsMap, vendorsMap, banksMap));

		List<Document> docs = new ArrayList<>();
		for (Client client : clients) {
			
			docs.add(dBObjectMapper.mapAsDbDocument(client.getClientId(), client));
		}

		mongoDbConnection.getCollection("clients").insertMany(docs);

		LOGGER.info("End of clients migration...");

	}

	private Map<Integer, List<ProductMerchantFee>> getProductMap(List<ProductMerchantFee> items) {
		Map<Integer, List<ProductMerchantFee>> result = new HashMap<>();

		for (ProductMerchantFee item : items) {

			if (result.containsKey(item.getClientId())) {
				result.get(item.getClientId()).add(item);
			}
			else {
				List<ProductMerchantFee> list = new ArrayList<>();
				list.add(item);
				result.put(item.getClientId(), list);
			}
		}
		return result;
	}

	private Map<Integer, List<BankVendor>> getVendoMap(List<BankVendor> items) {
		Map<Integer, List<BankVendor>> result = new HashMap<>();

		for (BankVendor item : items) {

			if (result.containsKey(item.getClientId())) {
				result.get(item.getClientId()).add(item);
			}
			else {
				List<BankVendor> list = new ArrayList<>();
				list.add(item);
				result.put(item.getClientId(), list);
			}
		}
		return result;
	}

	private Map<Integer, List<Bank>> getBankMap(List<Bank> items) {
		Map<Integer, List<Bank>> result = new HashMap<>();

		for (Bank item : items) {

			if (result.containsKey(item.getClientId())) {
				result.get(item.getClientId()).add(item);
			}
			else {
				List<Bank> list = new ArrayList<>();
				list.add(item);
				result.put(item.getClientId(), list);
			}
		}
		return result;
	}

	private Collection<? extends Client> updateClients(
			List<Client> clients, Map<Integer, List<ProductMerchantFee>> productsMap,
			Map<Integer, List<BankVendor>> vendorsMap, 
			Map<Integer, List<Bank>> banksMap) {
		
		
		for(Client client : clients) {
			
			if(productsMap.containsKey(client.getClientId()) 
			|| vendorsMap.containsKey(client.getClientId()) 
			|| banksMap.containsKey(client.getClientId())) {
				
				client.setProducts(productsMap.get(client.getClientId()));
				client.setVendors(vendorsMap.get(client.getClientId()));
				client.setBanks(banksMap.get(client.getClientId()));
				
			}
				
		}
		
		return clients;
	}


	public void migrateClientPricing() {
		
	}
	
	
	
}
