package com.cwt.bpg.cbt.tpromigration.service;

import java.util.*;
import java.util.stream.Collectors;

//import com.cwt.bpg.cbt.exchange.order.model.RemarkList;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.cwt.bpg.cbt.exchange.order.model.*;
import com.cwt.bpg.cbt.tpromigration.mongodb.config.MongoDbConnection;
import com.cwt.bpg.cbt.tpromigration.mongodb.mapper.DBObjectMapper;
import com.cwt.bpg.cbt.tpromigration.mssqldb.dao.*;
import com.cwt.bpg.cbt.tpromigration.mssqldb.model.Client;
import com.cwt.bpg.cbt.tpromigration.mssqldb.model.ProductList;
import com.cwt.bpg.cbt.tpromigration.mssqldb.model.Vendor;
import com.fasterxml.jackson.core.JsonProcessingException;

@Service
public class MigrationService {

	private static final Logger LOGGER = LoggerFactory.getLogger(MigrationService.class);

	private static final String AIRPORT_COLLECTION = "airports";
	private static final String CLIENT_COLLECTION = "clients_grace";

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
	private AirlineRuleDAOImpl airlineRuleDAO;

	@Autowired
	private ClientDAOImpl clientDAO;

	@Autowired
	private AirportDAO airportDAO;

    @Autowired
    private RemarkDAO remarkDAO;

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
		List<BaseProduct> products = productDAOFactory.getProductCodeDAO().listProductCodes();
		
		Map<String, BaseProduct> productsMap = products.stream()
				.collect(Collectors.toMap(BaseProduct::getProductCode, product -> product));
		String countryCode = System.getProperty("spring.profiles.default");

		vendorList.forEach(vendor -> {
			List<String> productCodes = vendor.getProductCodes();

			LOGGER.info("vendor:{}", vendor);
			LOGGER.info("vendor.getProductCodes():" + productCodes);

			productCodes.forEach(productCode -> {
				if (productsMap.get(productCode) != null)
					productsMap.get(productCode).getVendors().add(vendor);
			});

			vendor.setProductCodes(null);
		});

		@SuppressWarnings("rawtypes")
		ProductList productList = new ProductList();
		productList.setCountryCode(countryCode);
		productList.setProducts(new ArrayList<>(productsMap.values()));

		mongoDbConnection.getCollection("productList")
				.insertOne(dBObjectMapper.mapAsDbDocument(productList.getCountryCode(), productList));
	}

	@SuppressWarnings("unchecked")
	public void migrateAirports() throws JsonProcessingException {
		List<Airport> airports = airportDAO.getAirports();

		List<Document> docs = new ArrayList<>();

		for (Airport airport : airports) {
			docs.add(dBObjectMapper.mapAsDbDocument(airport.getCode(), airport));
		}

		mongoDbConnection.getCollection(AIRPORT_COLLECTION).insertMany(docs);
	}

	@SuppressWarnings("unchecked")
	public void migrateMerchantFees() throws JsonProcessingException {

		LOGGER.info("Started merchant fee migration...");

		List<MerchantFee> merchantFees = clientMerchantFeeDAO.listMerchantFees();

		for (MerchantFee merchantFee : merchantFees) {
			mongoDbConnection.getCollection("clientMerchantFee")
					.insertOne(dBObjectMapper.mapAsDbDocument(merchantFee));
		}

		LOGGER.info("End of merchant fee migration...");

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
		Map<Integer, List<CreditCardVendor>> ccsMap = getVendoMap(clientDAO.getCcs());
		Map<Integer, List<Bank>> banksMap = getBankMap(clientDAO.getBanks());
		Map<Integer, Map<String, ClientPricing>> clientPricingMaps = getClientPricingMaps(
				clientDAO.getClientPricings());
		Map<Integer, List<TransactionFee>> transactionFeeByPNR = getTransactionFeesMap(
				clientDAO.getTransactionFeeByPNR());
		Map<Integer, List<TransactionFee>> transactionFeeByCoupon = getTransactionFeesMap(
				clientDAO.getTransactionFeeByCoupon());
		Map<Integer, List<TransactionFee>> transactionFeeByTicket = getTransactionFeesMap(
				clientDAO.getTransactionFeeByTicket());
		List<Client> clients = clientDAO.getClients();

		Client defaultClient = new Client();
		defaultClient.setClientId(-1);
		defaultClient.setApplyMfBank(false);
		defaultClient.setApplyMfCc(false);
		clients.add(defaultClient);

		updateClients(clients,
				productsMap,
				ccsMap,
				banksMap,
				clientPricingMaps,
				transactionFeeByPNR,
				transactionFeeByCoupon,
				transactionFeeByTicket);

		List<Document> docs = new ArrayList<>();
		for (Client client : clients) {

			docs.add(dBObjectMapper.mapAsDbDocument(client.getClientId(), client));
		}

		mongoDbConnection.getCollection(CLIENT_COLLECTION).insertMany(docs);

		LOGGER.info("End of clients migration...");

	}

	private Map<Integer, List<TransactionFee>> getTransactionFeesMap(List<TransactionFee> transactionFees) {
		Map<Integer, List<TransactionFee>> transactionFeesMap = new HashMap<>();
		int previousGroupId = 0;
		List<TransactionFee> groupedTransactionFees = null;
		for (TransactionFee transactionFee : transactionFees) {
			if (previousGroupId != transactionFee.getFeeId()) {
				groupedTransactionFees = new ArrayList<>();
			}
			groupedTransactionFees.add(transactionFee);
			previousGroupId = transactionFee.getFeeId();
			transactionFeesMap.put(previousGroupId, groupedTransactionFees);
		}
		return transactionFeesMap;
	}

	private Map<Integer, Map<String, ClientPricing>> getClientPricingMaps(
			List<ClientPricing> clientPricings) {
		Map<Integer, Map<String, ClientPricing>> result = new HashMap<>();
		int previousCmpId = 0;
		Map<String, ClientPricing> clientPricingMap = null;
		for (ClientPricing clientPricing : clientPricings) {
			if (previousCmpId != clientPricing.getCmpid()) {
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

	private Collection<? extends Client> updateClients(List<Client> clients,
			Map<Integer, List<ProductMerchantFee>> productsMap,
			Map<Integer, List<CreditCardVendor>> vendorsMap, Map<Integer, List<Bank>> banksMap,
			Map<Integer, Map<String, ClientPricing>> clientPricingMaps,
			Map<Integer, List<TransactionFee>> transactionFeeByPNR,
			Map<Integer, List<TransactionFee>> transactionFeeByCoupon,
			Map<Integer, List<TransactionFee>> transactionFeeByTicket) {

		for (Client client : clients) {

			if (productsMap.containsKey(client.getClientId()) 
				|| vendorsMap.containsKey(client.getClientId())
				|| banksMap.containsKey(client.getClientId())) {

				client.setMfProducts(productsMap.get(client.getClientId()));
				client.setMfCcs(vendorsMap.get(client.getClientId()));
				client.setMfBanks(banksMap.get(client.getClientId()));
			}
			List<ClientPricing> clientPricings = new ArrayList<>();
			if (clientPricingMaps.containsKey(client.getCmpid())) {
				Map<String, ClientPricing> clientPricingMap = clientPricingMaps.get(client.getCmpid());

				for (String tripType : clientPricingMap.keySet()) {
					ClientPricing clientPricing = clientPricingMap.get(tripType);
					String feeOption = clientPricing.getFeeOption();
					if ("P".equals(feeOption)) {
						clientPricing.setTransactionFees(transactionFeeByPNR.get(clientPricing.getGroup()));
					}
					else if ("C".equals(feeOption)) {
						clientPricing
								.setTransactionFees(transactionFeeByCoupon.get(clientPricing.getGroup()));
					}
					else if ("T".equals(feeOption)) {
						clientPricing
								.setTransactionFees(transactionFeeByTicket.get(clientPricing.getGroup()));
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

	@SuppressWarnings("unchecked")
	public void migrateRemarks() throws JsonProcessingException {
		String countryCode = System.getProperty("spring.profiles.default");
		for(Remark remark: remarkDAO.getRemarks()) {
			remark.setCountryCode(countryCode);
			mongoDbConnection.getCollection("remarkList")
					.insertOne(dBObjectMapper.mapAsDbDocument(remark));
		}
//		RemarkList remarkList = new RemarkList();
//		remarkList.setCountryCode(countryCode);
//		remarkList.setRemarks(remarkDAO.getRemarks());
//		mongoDbConnection.getCollection("remarkList")
//				.insertOne(dBObjectMapper.mapAsDbDocument(remarkList.getCountryCode(), remarkList));
	}

}
