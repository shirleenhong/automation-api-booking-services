package com.cwt.bpg.cbt.tpromigration.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.cwt.bpg.cbt.tpromigration.mssqldb.dao.*;
import com.cwt.bpg.cbt.tpromigration.mssqldb.model.*;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.cwt.bpg.cbt.tpromigration.mongodb.config.MongoDbConnection;
import com.cwt.bpg.cbt.tpromigration.mongodb.mapper.DBObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;

@Service
public class MigrationService {

	private static final Logger LOGGER = LoggerFactory.getLogger(MigrationService.class);

	@Autowired
	private MongoDbConnection mongoDbConnection;

	@Autowired
	private DBObjectMapper dBObjectMapper;

	@Autowired
	private VendorDAOFactory vendorDAOFactory;

	@Autowired
	private ProductDAOFactory productDAOFactory;

	@Autowired
	private ClientMerchantFeeDAO clientMerchantFeeDAO;

	@Autowired
	private CurrencyDAO currencyDAO;

	@Autowired
	private AirlineRuleDAOImpl airlineRuleDAO;

	@Autowired
	private ClientDAOImpl clientDAO;

	@Autowired
	private CityDAO cityDAO;

	@Value("${com.cwt.tpromigration.mongodb.dbuser}")
	private String dbUser;

	@Value("${com.cwt.tpromigration.mongodb.dbpwd}")
	private String dbPwd;

	@Value("${com.cwt.tpromigration.mongodb.dbname}")
	private String dbName;

	@SuppressWarnings("unchecked")
	public void migrateProductList() throws JsonProcessingException {

		LOGGER.info("start migration...");

		List<Vendor> vendorList = vendorDAOFactory.getVendorDAO().listVendors();
		List<Product> products = productDAOFactory.getProductCodeDAO().listProductCodes();
		Map<String, Product> productsMap = products.stream().collect(Collectors.toMap(Product::getProductCode, product -> product));
		String countryCode = System.getProperty("spring.profiles.default");

		vendorList.forEach(vendor -> {
			vendor.setCountryCode(countryCode);
			List<String> productCodes = vendor.getProductCodes();

			LOGGER.info("vendor:{}", vendor);
			LOGGER.info("vendor.getProductCodes():" + productCodes);

			productCodes.forEach(productCode -> {
				if(productsMap.get(productCode)!=null)productsMap.get(productCode).getVendors().add(vendor);
			});

			vendor.setProductCodes(null);
		});

		ProductList productList = new ProductList();
		productList.setCountryCode(countryCode);
		productList.setProducts(new ArrayList<>(productsMap.values()));

		mongoDbConnection.getCollection("productList").insertOne(dBObjectMapper.mapAsDbDocument(productList.getCountryCode(),productList));
	}

	@SuppressWarnings("unchecked")
	public void migrateCities() throws JsonProcessingException {
		List<City> cities = cityDAO.getCities();

		List<Document> docs = new ArrayList<>();

		for (City city : cities) {
			docs.add(dBObjectMapper.mapAsDbDocument(city));
		}

		mongoDbConnection.getCollection("cities").insertMany(docs);
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
		Map<Integer, List<CreditCardVendor>> vendorsMap = getVendoMap(clientDAO.getVendors());
		Map<Integer, List<Bank>> banksMap = getBankMap(clientDAO.getBanks());
		Map<Integer, Map<String, ClientPricing>> clientPricingMaps = getClientPricingMaps(clientDAO.getClientPricings());
		Map<Integer, List<TransactionFee>> transactionFeeByPNR = getTransactionFeesMap(clientDAO.getTransactionFeeByPNR());
		Map<Integer, List<TransactionFee>> transactionFeeByCoupon = getTransactionFeesMap(clientDAO.getTransactionFeeByCoupon());
		Map<Integer, List<TransactionFee>> transactionFeeByTicket = getTransactionFeesMap(clientDAO.getTransactionFeeByTicket());
		List<Client> clients = clientDAO.getClients();
		
		Client defaultClient = new Client();
		defaultClient.setClientId(-1);
		clients.add(defaultClient);

		updateClients(clients, 
				productsMap, 
				vendorsMap, 
				banksMap, 
				clientPricingMaps, 
				transactionFeeByPNR, 
				transactionFeeByCoupon, 
				transactionFeeByTicket);

		List<Document> docs = new ArrayList<>();
		for (Client client : clients) {
			
			docs.add(dBObjectMapper.mapAsDbDocument(client.getClientId(), client));
		}

		mongoDbConnection.getCollection("clients").insertMany(docs);

		LOGGER.info("End of clients migration...");

	}

	private Map<Integer, List<TransactionFee>> getTransactionFeesMap(List<TransactionFee> transactionFees) {
		Map<Integer, List<TransactionFee>> transactionFeesMap = new HashMap<>();
		int previousGroupId = 0;
		List<TransactionFee> groupedTransactionFees = null;
		for (TransactionFee transactionFee: transactionFees) {
			if(previousGroupId != transactionFee.getFeeId()) {
				groupedTransactionFees = new ArrayList<>();
			}
			groupedTransactionFees.add(transactionFee);
			previousGroupId = transactionFee.getFeeId();
			transactionFeesMap.put(previousGroupId, groupedTransactionFees);
		}
		return transactionFeesMap;
	}

	private Map<Integer, Map<String, ClientPricing>> getClientPricingMaps(List<ClientPricing> clientPricings) {
		Map<Integer, Map<String, ClientPricing>> result = new HashMap<>();
		int previousCmpId = 0;
		Map<String, ClientPricing> clientPricingMap = null;
		for (ClientPricing clientPricing: clientPricings) {
			if(previousCmpId != clientPricing.getCmpid()) {
				clientPricingMap = new HashMap<>();
			}
			previousCmpId = clientPricing.getCmpid();
			clientPricingMap.put(clientPricing.getTripType(), clientPricing);
			result.put(previousCmpId, clientPricingMap);
		}
		return result;
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

	private Map<Integer, List<CreditCardVendor>> getVendoMap(List<CreditCardVendor> items) {
		Map<Integer, List<CreditCardVendor>> result = new HashMap<>();

		for (CreditCardVendor item : items) {

			if (result.containsKey(item.getClientId())) {
				result.get(item.getClientId()).add(item);
			}
			else {
				List<CreditCardVendor> list = new ArrayList<>();
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
			Map<Integer, List<CreditCardVendor>> vendorsMap, 
			Map<Integer, List<Bank>> banksMap, Map<Integer, 
			Map<String, ClientPricing>> clientPricingMaps, 
			Map<Integer, List<TransactionFee>> transactionFeeByPNR, 
			Map<Integer, List<TransactionFee>> transactionFeeByCoupon, 
			Map<Integer, List<TransactionFee>> transactionFeeByTicket) {
		
		for(Client client : clients) {		
			
			if(productsMap.containsKey(client.getClientId()) 
			|| vendorsMap.containsKey(client.getClientId()) 
			|| banksMap.containsKey(client.getClientId())) {
				
				client.setMfProducts(productsMap.get(client.getClientId()));
				client.setMfCcs(vendorsMap.get(client.getClientId()));
				client.setMfBanks(banksMap.get(client.getClientId()));
			}
			List<ClientPricing> clientPricings = new ArrayList<>();
			if(clientPricingMaps.containsKey(client.getCmpid())) {
				Map<String, ClientPricing> clientPricingMap = clientPricingMaps.get(client.getCmpid());
				Set test = clientPricingMap.keySet();
				for(String tripType: clientPricingMap.keySet()) {
					ClientPricing clientPricing = clientPricingMap.get(tripType);
					String feeOption = clientPricing.getFeeOption();
					if("P".equals(feeOption)) {
						clientPricing.setTransactionFees(transactionFeeByPNR.get(clientPricing.getGroup()));
					}else if("C".equals(feeOption)) {
						clientPricing.setTransactionFees(transactionFeeByCoupon.get(clientPricing.getGroup()));
					}else if("T".equals(feeOption)) {
						clientPricing.setTransactionFees(transactionFeeByTicket.get(clientPricing.getGroup()));
					}
					clientPricings.add(clientPricing);
				}
				client.setClientPricings(clientPricings);
			}
		}
		
		return clients;
	}


	public void migrateClientPricing() {
		
	}
	
	
	
}
