package com.cwt.bpg.cbt.tpromigration.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.cwt.bpg.cbt.air.transaction.model.AirTransaction;
import com.cwt.bpg.cbt.air.transaction.model.BookingClass;
import com.cwt.bpg.cbt.air.transaction.model.PassthroughType;
import com.cwt.bpg.cbt.exchange.order.model.AirlineRule;
import com.cwt.bpg.cbt.exchange.order.model.Airport;
import com.cwt.bpg.cbt.exchange.order.model.Bank;
import com.cwt.bpg.cbt.exchange.order.model.BaseProduct;
import com.cwt.bpg.cbt.exchange.order.model.ClientPricing;
import com.cwt.bpg.cbt.exchange.order.model.ContactInfo;
import com.cwt.bpg.cbt.exchange.order.model.ContactInfoType;
import com.cwt.bpg.cbt.exchange.order.model.CreditCardVendor;
import com.cwt.bpg.cbt.exchange.order.model.MerchantFee;
import com.cwt.bpg.cbt.exchange.order.model.ProductMerchantFee;
import com.cwt.bpg.cbt.exchange.order.model.Remark;
import com.cwt.bpg.cbt.exchange.order.model.TransactionFee;
import com.cwt.bpg.cbt.tpromigration.mongodb.config.MongoDbConnection;
import com.cwt.bpg.cbt.tpromigration.mongodb.mapper.DBObjectMapper;
import com.cwt.bpg.cbt.tpromigration.mssqldb.dao.AirTransactionDAOImpl;
import com.cwt.bpg.cbt.tpromigration.mssqldb.dao.AirlineRuleDAOImpl;
import com.cwt.bpg.cbt.tpromigration.mssqldb.dao.AirportDAO;
import com.cwt.bpg.cbt.tpromigration.mssqldb.dao.ClientDAOImpl;
import com.cwt.bpg.cbt.tpromigration.mssqldb.dao.ClientMerchantFeeDAO;
import com.cwt.bpg.cbt.tpromigration.mssqldb.dao.ProductDAOFactory;
import com.cwt.bpg.cbt.tpromigration.mssqldb.dao.RemarkDAO;
import com.cwt.bpg.cbt.tpromigration.mssqldb.dao.VendorDAOFactory;
import com.cwt.bpg.cbt.tpromigration.mssqldb.model.Client;
import com.cwt.bpg.cbt.tpromigration.mssqldb.model.ProductList;
import com.cwt.bpg.cbt.tpromigration.mssqldb.model.Vendor;
import com.fasterxml.jackson.core.JsonProcessingException;

@Service
public class MigrationService {

	private static final Logger LOGGER = LoggerFactory.getLogger(MigrationService.class);

	private static final String AIRPORT_COLLECTION = "airports";
	private static final String CLIENT_COLLECTION = "clients";
	private static final String AIR_TRANSACTION_COLLECTION = "airTransactions";

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

	@Autowired
	private AirTransactionDAOImpl airTransactionDAOImpl;

	@Value("${com.cwt.tpromigration.mongodb.dbuser}")
	private String dbUser;

	@Value("${com.cwt.tpromigration.mongodb.dbpwd}")
	private String dbPwd;

	@Value("${com.cwt.tpromigration.mongodb.dbname}")
	private String dbName;
	
	private String countryCode;

    @SuppressWarnings("unchecked")
	public void migrateProductList() throws JsonProcessingException {

		LOGGER.info("start migration...");

		List<ContactInfo> contactInfoList = vendorDAOFactory.getVendorDAO(countryCode).listVendorContactInfo();
		List<Vendor> vendorList = vendorDAOFactory.getVendorDAO(countryCode).listVendors();
		List<BaseProduct> products = productDAOFactory.getProductCodeDAO(countryCode).listProductCodes();
		
		Map<String, BaseProduct> productsMap = products.stream()
				.collect(Collectors.toMap(BaseProduct::getProductCode, product -> product));
		
		vendorList.forEach(vendor -> {
			List<String> productCodes = vendor.getProductCodes();

			LOGGER.info("vendor:{}", vendor);
			LOGGER.info("vendor.getProductCodes():" + productCodes);

			if (!ObjectUtils.isEmpty(contactInfoList)) {
			
				List<ContactInfo> contactList = new ArrayList<>();
				
				contactInfoList.forEach(ci -> {
					if (vendor.getCode().equals(ci.getVendorNumber())) {
						setMigratedContactInfo(contactList, ci.getType(), ci.getDetail(),
								ci.isPreferred());
					}

				});
				LOGGER.info("size of contact info saved in vendor " + contactList.size());
				vendor.setContactInfo(contactList);
			}
			else {
				List<ContactInfo> contactList = new ArrayList<>();
				
				if (!ObjectUtils.isEmpty(vendor.getEmail())) {
					setMigratedContactInfo(contactList, ContactInfoType.EMAIL,
							vendor.getEmail(), true);
				}
				if (!ObjectUtils.isEmpty(vendor.getFaxNumber())) {
					setMigratedContactInfo(contactList, ContactInfoType.FAX,
							vendor.getFaxNumber(), true);
				}
				if(!ObjectUtils.isEmpty(vendor.getContactNo())) {
					setMigratedContactInfo(contactList, ContactInfoType.PHONE,
							vendor.getContactNo(), true);
				}
				LOGGER.info("size of contact info saved in vendor " + contactList.size());
				vendor.setContactInfo(contactList);
			}
			
			vendor.setEmail(null);
			vendor.setFaxNumber(null);
			vendor.setContactNo(null);
			
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

	private void setMigratedContactInfo(List<ContactInfo> contactList,
			ContactInfoType type, String detail, Boolean preferred) {
		
		ContactInfo contactInfo = new ContactInfo();
		contactInfo.setType(type);
		contactInfo.setDetail(detail);
		contactInfo.setPreferred(preferred);
		contactList.add(contactInfo);
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

		List<MerchantFee> merchantFees = clientMerchantFeeDAO.listMerchantFees(countryCode);

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


	@SuppressWarnings("unchecked")
	public void migrateRemarks() throws JsonProcessingException {
		
		for(Remark remark: remarkDAO.getRemarks()) {
			remark.setCountryCode(countryCode);
			mongoDbConnection.getCollection("remarkList")
					.insertOne(dBObjectMapper.mapAsDbDocument(remark));
		}
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	@SuppressWarnings({ "unchecked" })
	public void migratePassthroughs() throws JsonProcessingException {
		List<AirTransaction> airTransactions = airTransactionDAOImpl.getList();

		List<Document> docs = new ArrayList<>();
		
		for (AirTransaction airTransaction : airTransactions) {

			AirTransaction airTransaction2 = new AirTransaction(airTransaction);
			List<BookingClass> cwtBkClassList = new ArrayList<>();
			List<BookingClass> airlineBkClassList = new ArrayList<>();
			
			Map<String, Object> mhBookingClasses = formMHBookingClass();
			Iterator<String> mhKeySetIterator = mhBookingClasses.keySet().iterator();
			
			Map<String, Object> tgFullPassBookingClasses = formTGFullPassBookingClasses();
			Iterator<String> tgFullKeySetIterator = tgFullPassBookingClasses.keySet().iterator();
			
			Map<String, Object> tgNonPassBookingClasses = formTGNonPassBookingClasses();
			Iterator<String> tgNonkeySetIterator = tgNonPassBookingClasses.keySet().iterator();
			
			if (airTransaction.getPassthroughTypeOriginal().equals("SP")
					&& (airTransaction.getAirlineCode().equals("MH"))) {

				while(mhKeySetIterator.hasNext()){
					String key = mhKeySetIterator.next();
					BookingClass bookingClass = new BookingClass();
					bookingClass.setCode(key);
					
					if(key!="O" && key!="Q") {
						airlineBkClassList.add(bookingClass);
					}else {
						cwtBkClassList.add(bookingClass);
					}
				}
			
				airTransaction.setBookingClass(cwtBkClassList);
				airTransaction.setPassthroughType(PassthroughType.CWT);
				
				airTransaction2.setBookingClass(airlineBkClassList);
				airTransaction2.setPassthroughType(PassthroughType.AIRLINE);
				
				airTransaction2.setPassthroughTypeOriginal(null);
				docs.add(dBObjectMapper.mapAsDbDocument(airTransaction2));
			}	
			else if(airTransaction.getPassthroughTypeOriginal().equals("SP")
					&& (airTransaction.getAirlineCode().equals("TG"))) {
								
				while(tgFullKeySetIterator.hasNext()){
					String key = tgFullKeySetIterator.next();
					BookingClass bookingClass = new BookingClass();
					bookingClass.setCode(key);
					airlineBkClassList.add(bookingClass);
				}
	
				while(tgNonkeySetIterator.hasNext()){
					String key = tgNonkeySetIterator.next();
					BookingClass bookingClass = new BookingClass();
					bookingClass.setCode(key);
					cwtBkClassList.add(bookingClass);
				}

				airTransaction.setBookingClass(cwtBkClassList);
				airTransaction.setPassthroughType(PassthroughType.CWT);
				
				airTransaction2.setBookingClass(airlineBkClassList);
				airTransaction2.setPassthroughType(PassthroughType.AIRLINE);
				
				airTransaction2.setPassthroughTypeOriginal(null);
				docs.add(dBObjectMapper.mapAsDbDocument(airTransaction2));
			}
			airTransaction.setPassthroughTypeOriginal(null);
			docs.add(dBObjectMapper.mapAsDbDocument(airTransaction));			
		}

		mongoDbConnection.getCollection(AIR_TRANSACTION_COLLECTION).insertMany(docs);

		System.out.println("Finished migration of airTransactions");
	}

	private Map<String, Object> formTGNonPassBookingClasses() {
		
		Map<String, Object> tgNonPassBookingClasses = new HashMap<>();
		tgNonPassBookingClasses.put("T", null);
		tgNonPassBookingClasses.put("K", null);
		tgNonPassBookingClasses.put("S", null);
		tgNonPassBookingClasses.put("V", null);
		tgNonPassBookingClasses.put("W", null);
		tgNonPassBookingClasses.put("L", null);
		
		return tgNonPassBookingClasses;
	}

	private Map<String, Object> formTGFullPassBookingClasses() {
		
		Map<String, Object> tgFullPassBookingClasses = new HashMap<>();
		tgFullPassBookingClasses.put("F", null);
		tgFullPassBookingClasses.put("A", null);
		tgFullPassBookingClasses.put("P", null);
		tgFullPassBookingClasses.put("C", null);
		tgFullPassBookingClasses.put("D", null);
		tgFullPassBookingClasses.put("J", null);
		tgFullPassBookingClasses.put("Z", null);
		tgFullPassBookingClasses.put("Y", null);
		tgFullPassBookingClasses.put("B", null);
		tgFullPassBookingClasses.put("M", null);
		tgFullPassBookingClasses.put("H", null);
		tgFullPassBookingClasses.put("Q", null);
		
		return tgFullPassBookingClasses;
	}

	private Map<String, Object> formMHBookingClass() {
		
		Map<String, Object> mhParameters = new HashMap<>();
		mhParameters.put("O", null);
		mhParameters.put("Q", null);
		mhParameters.put("F", null);
		mhParameters.put("A", null);
		mhParameters.put("P", null);
		mhParameters.put("J", null);
		mhParameters.put("C", null);
		mhParameters.put("D", null);
		mhParameters.put("Z", null);
		mhParameters.put("I", null);
		mhParameters.put("U", null);
		mhParameters.put("Y", null);
		mhParameters.put("B", null);
		mhParameters.put("H", null);
		mhParameters.put("K", null);
		mhParameters.put("M", null);
		mhParameters.put("L", null);
		mhParameters.put("V", null);
		mhParameters.put("S", null);
		mhParameters.put("N", null);
		mhParameters.put("E", null);
		mhParameters.put("X", null);
		mhParameters.put("G", null);

		return mhParameters;
	}

}
