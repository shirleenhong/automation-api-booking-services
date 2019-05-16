package com.cwt.bpg.cbt.tpromigration.service;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.naming.NamingException;
import javax.naming.directory.Attributes;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.LdapContextSource;
import org.springframework.ldap.filter.AndFilter;
import org.springframework.ldap.filter.EqualsFilter;
import org.springframework.ldap.support.LdapUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.cwt.bpg.cbt.air.contract.model.AirContract;
import com.cwt.bpg.cbt.air.transaction.model.AirTransaction;
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
import com.cwt.bpg.cbt.exchange.order.model.india.AirMiscInfo;
import com.cwt.bpg.cbt.tpromigration.mongodb.config.MongoDbConnection;
import com.cwt.bpg.cbt.tpromigration.mongodb.mapper.DBObjectMapper;
import com.cwt.bpg.cbt.tpromigration.mssqldb.dao.AgentDAOImpl;
import com.cwt.bpg.cbt.tpromigration.mssqldb.dao.AirContractDAOImpl;
import com.cwt.bpg.cbt.tpromigration.mssqldb.dao.AirTransactionDAOImpl;
import com.cwt.bpg.cbt.tpromigration.mssqldb.dao.AirlineRuleDAOImpl;
import com.cwt.bpg.cbt.tpromigration.mssqldb.dao.AirportDAO;
import com.cwt.bpg.cbt.tpromigration.mssqldb.dao.ClientDAOImpl;
import com.cwt.bpg.cbt.tpromigration.mssqldb.dao.ClientMerchantFeeDAO;
import com.cwt.bpg.cbt.tpromigration.mssqldb.dao.ProductDAOFactory;
import com.cwt.bpg.cbt.tpromigration.mssqldb.dao.RemarkDAO;
import com.cwt.bpg.cbt.tpromigration.mssqldb.dao.VendorDAOFactory;
import com.cwt.bpg.cbt.tpromigration.mssqldb.model.AgentInfo;
import com.cwt.bpg.cbt.tpromigration.mssqldb.model.AirVariables;
import com.cwt.bpg.cbt.tpromigration.mssqldb.model.Client;
import com.cwt.bpg.cbt.tpromigration.mssqldb.model.MerchantFeeAbsorb;
import com.cwt.bpg.cbt.tpromigration.mssqldb.model.ProductList;
import com.cwt.bpg.cbt.tpromigration.mssqldb.model.TblAgent;
import com.cwt.bpg.cbt.tpromigration.mssqldb.model.TblAgentConfig;
import com.cwt.bpg.cbt.tpromigration.mssqldb.model.Vendor;
import com.fasterxml.jackson.core.JsonProcessingException;

@Service
public class MigrationService {

	private static final Logger LOGGER = LoggerFactory.getLogger(MigrationService.class);

	private static final String AIRPORT_COLLECTION = "airports";
	private static final String CLIENT_COLLECTION = "clients";
	private static final String AIR_TRANSACTION_COLLECTION = "airTransactions";
	private static final String AIR_CONTRACTS_COLLECTION = "airContracts";
	private static final String AIR_MISC_INFO_COLLECTION = "airMiscInfo";
	private static final String PRODUCTS_COLLECTION = "productList";
	private static final String AGENT_COLLECTION = "agentInfo";

	@Autowired
	private MongoDbConnection mongoDbConnection;

	@Autowired
	private DBObjectMapper dBObjectMapper;

	@Autowired
	private LdapContextSource ldapContextSource;

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

	@Autowired
	private AgentDAOImpl agentDAOImpl;

	@Autowired
	private AirContractDAOImpl airContractDAOImpl;

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
		List<MerchantFeeAbsorb> noMerchantFeeList = vendorDAOFactory.getVendorDAO(countryCode).listNoMerchantFee();

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
						setMigratedContactInfo(contactList, ci.getType(), ci.getDetail(), ci.isPreferred());
					}

				});
				LOGGER.info("size of contact info saved in vendor " + contactList.size());
				vendor.setContactInfo(contactList);
			} else {
				List<ContactInfo> contactList = new ArrayList<>();

				if (!ObjectUtils.isEmpty(vendor.getEmail())) {
					setMigratedContactInfo(contactList, ContactInfoType.EMAIL, vendor.getEmail(), true);
				}
				if (!ObjectUtils.isEmpty(vendor.getFaxNumber())) {
					setMigratedContactInfo(contactList, ContactInfoType.FAX, vendor.getFaxNumber(), true);
				}
				if (!ObjectUtils.isEmpty(vendor.getContactNo())) {
					setMigratedContactInfo(contactList, ContactInfoType.PHONE, vendor.getContactNo(), true);
				}
				LOGGER.info("size of contact info saved in vendor " + contactList.size());
				vendor.setContactInfo(contactList);
			}

			vendor.setEmail(null);
			vendor.setFaxNumber(null);
			vendor.setContactNo(null);

			productCodes.forEach(productCode -> {
				if (productsMap.get(productCode) != null) {
					Vendor vendorClone = traverseNoMerchantFeeList(noMerchantFeeList, vendor, productCode);
					vendorClone.setProductCodes(null);
					productsMap.get(productCode).getVendors().add(vendorClone);
				}
			});

		});

		@SuppressWarnings("rawtypes")
		ProductList productList = new ProductList();
		productList.setCountryCode(countryCode);
		productList.setProducts(new ArrayList<>(productsMap.values()));

		mongoDbConnection.getCollection(PRODUCTS_COLLECTION)
				.insertOne(dBObjectMapper.mapAsDbDocument(productList.getCountryCode(), productList));
	}

	private Vendor traverseNoMerchantFeeList(List<MerchantFeeAbsorb> noMerchantFeeList, Vendor vendor,
			String productCode) {

		Vendor vendorClone = new Vendor();
		try {
			BeanUtils.copyProperties(vendorClone, vendor);
		} catch (IllegalAccessException e) {
			LOGGER.error("IllegalAccessException", e);
		} catch (InvocationTargetException e) {
			LOGGER.error("InvocationTargetException", e);
		}

		for (MerchantFeeAbsorb mf : noMerchantFeeList) {
			if (productCode.equals(mf.getProductCode()) && vendorClone.getCode().equals(mf.getVendorNumber())) {
				vendorClone.setMerchantFeeAbsorb(true);
				break;
			}
		}
		return vendorClone;
	}

	private void setMigratedContactInfo(List<ContactInfo> contactList, ContactInfoType type, String detail,
			Boolean preferred) {

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
			mongoDbConnection.getCollection("clientMerchantFee").insertOne(dBObjectMapper.mapAsDbDocument(merchantFee));
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
		Map<Integer, Map<Integer, ClientPricing>> clientPricingMaps = getClientPricingMaps(
				clientDAO.getClientPricings());
		Map<Integer, List<TransactionFee>> transactionFeeByPNR = getTransactionFeesMap(
				clientDAO.getTransactionFeeByPNR());
		Map<Integer, List<TransactionFee>> transactionFeeByCoupon = getTransactionFeesMap(
				clientDAO.getTransactionFeeByCoupon());
		Map<Integer, List<TransactionFee>> transactionFeeByTicket = getTransactionFeesMap(
				clientDAO.getTransactionFeeByTicket());
		List<Client> clients = clientDAO.getClients();
		List<Client> clientsGstin = clientDAO.getClientsWithGstin();
		List<AirVariables> airVariables = clientDAO.getAirVariables();

		Client defaultClient = new Client();
		defaultClient.setClientId(-1);
		defaultClient.setApplyMfBank(false);
		defaultClient.setApplyMfCc(false);
		clients.add(defaultClient);

		updateClients(clients, clientsGstin, productsMap, ccsMap, banksMap, clientPricingMaps, airVariables,
				transactionFeeByPNR, transactionFeeByCoupon, transactionFeeByTicket);

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

	private Map<Integer, Map<Integer, ClientPricing>> getClientPricingMaps(List<ClientPricing> clientPricings) {
		Map<Integer, Map<Integer, ClientPricing>> result = new HashMap<>();
		int previousCmpId = 0;
		Integer i = 0;
		Map<Integer, ClientPricing> clientPricingMap = null;
		for (ClientPricing clientPricing : clientPricings) {
			if (previousCmpId != clientPricing.getCmpid()) {
				clientPricingMap = new HashMap<>();
				i = 0;
			}
			previousCmpId = clientPricing.getCmpid();
			clientPricingMap.put(i, clientPricing);
			result.put(previousCmpId, clientPricingMap);
			i++;
		}
		return result;
	}

	private Map<Integer, List<ProductMerchantFee>> getProductMap(List<ProductMerchantFee> items) {
		Map<Integer, List<ProductMerchantFee>> result = new HashMap<>();

		for (ProductMerchantFee item : items) {

			if (result.containsKey(item.getClientId())) {
				result.get(item.getClientId()).add(item);
			} else {
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
			} else {
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
			} else {
				List<Bank> list = new ArrayList<>();
				list.add(item);
				result.put(item.getClientId(), list);
			}
		}
		return result;
	}

	private Collection<? extends Client> updateClients(List<Client> clients, List<Client> clientsGstin,
			Map<Integer, List<ProductMerchantFee>> productsMap, Map<Integer, List<CreditCardVendor>> vendorsMap,
			Map<Integer, List<Bank>> banksMap, Map<Integer, Map<Integer, ClientPricing>> clientPricingMaps,
			List<AirVariables> airVariables, Map<Integer, List<TransactionFee>> transactionFeeByPNR,
			Map<Integer, List<TransactionFee>> transactionFeeByCoupon,
			Map<Integer, List<TransactionFee>> transactionFeeByTicket) {

		for (Client client : clients) {

			if (productsMap.containsKey(client.getClientId()) || vendorsMap.containsKey(client.getClientId())
					|| banksMap.containsKey(client.getClientId())) {

				client.setMfProducts(productsMap.get(client.getClientId()));
				client.setMfCcs(vendorsMap.get(client.getClientId()));
				client.setMfBanks(banksMap.get(client.getClientId()));
			}
			List<ClientPricing> clientPricings = new ArrayList<>();
			if (clientPricingMaps.containsKey(client.getCmpid())) {
				Map<Integer, ClientPricing> clientPricingMap = clientPricingMaps.get(client.getCmpid());

				for (ClientPricing clientPricing : clientPricingMap.values()) {

					for (AirVariables airVariable : airVariables) {
						if (clientPricing.getFieldId().equals(airVariable.getFieldId())) {
							clientPricing.setFeeName(airVariable.getFieldName());
							break;
						}
					}

					if (clientPricing.getFieldId() == 5) {

						String feeOption = clientPricing.getFeeOption();
						if ("P".equals(feeOption)) {
							clientPricing.setTransactionFees(transactionFeeByPNR.get(clientPricing.getValue()));
						} else if ("C".equals(feeOption)) {
							clientPricing.setTransactionFees(transactionFeeByCoupon.get(clientPricing.getValue()));
						} else if ("T".equals(feeOption)) {
							clientPricing.setTransactionFees(transactionFeeByTicket.get(clientPricing.getValue()));
						}
					}
					clientPricings.add(clientPricing);
				}
				client.setClientPricings(clientPricings);
			}

			for (Client clientGstin : clientsGstin) {
				if (client.getClientAccountNumber() != null && clientGstin.getClientAccountNumber() != null
						&& clientGstin.getClientAccountNumber().equals(client.getClientAccountNumber())) {
					client.setGstin(clientGstin.getGstin());
					break;
				}
			}
		}

		return clients;
	}

	@SuppressWarnings("unchecked")
	public void migrateRemarks() throws JsonProcessingException {

		for (Remark remark : remarkDAO.getRemarks()) {
			remark.setCountryCode(countryCode);
			mongoDbConnection.getCollection("remarkList").insertOne(dBObjectMapper.mapAsDbDocument(remark));
		}
	}

	@SuppressWarnings("unchecked")
	public void migrateAirContracts() throws JsonProcessingException {

		LOGGER.info("Started client air contracts migration...");
		List<AirContract> airContracts = airContractDAOImpl.getList();
		List<Document> docs = new ArrayList<>();
		for (AirContract airContract : airContracts) {
			airContract.setCountryCode(countryCode);
			docs.add(dBObjectMapper.mapAsDbDocument(airContract));
		}

		mongoDbConnection.getCollection(AIR_CONTRACTS_COLLECTION).insertMany(docs);

		LOGGER.info("End of client air contracts migration...");
	}

	@SuppressWarnings("unchecked")
	public void migrateAirMiscInfo() throws JsonProcessingException {

		LOGGER.info("Started air misc info migration...");
		List<AirMiscInfo> airMiscInfoList = clientDAO.getAirMiscInfo();
		List<Document> docs = new ArrayList<>();
		for (AirMiscInfo airMiscInfo : airMiscInfoList) {
			docs.add(dBObjectMapper.mapAsDbDocument(airMiscInfo));
		}

		mongoDbConnection.getCollection(AIR_MISC_INFO_COLLECTION).insertMany(docs);

		LOGGER.info("End of air misc info migration...");
	}

	@SuppressWarnings({ "unchecked" })
	public void migratePassthroughs() throws JsonProcessingException {
		List<AirTransaction> airTransactionsFPNP = airTransactionDAOImpl.getList(false);
		List<AirTransaction> airTransactionsSP = airTransactionDAOImpl.getList(true);

		List<Document> docs = new ArrayList<>();

		for (AirTransaction airTransaction : airTransactionsFPNP) {
			docs.add(dBObjectMapper.mapAsDbDocument(airTransaction));
		}

		for (AirTransaction airTransaction : airTransactionsSP) {

			AirTransaction airTransaction2 = new AirTransaction(airTransaction);
			List<String> cwtBkClassList = formBookingClasses(airTransaction.getAirlineCode(),
					PassthroughType.CWT.getCode());
			List<String> airlineBkClassList = formBookingClasses(airTransaction.getAirlineCode(),
					PassthroughType.AIRLINE.getCode());

			airTransaction.setBookingClasses(cwtBkClassList);
			airTransaction.setPassthroughType(PassthroughType.CWT);

			airTransaction2.setBookingClasses(airlineBkClassList);
			airTransaction2.setPassthroughType(PassthroughType.AIRLINE);

			docs.add(dBObjectMapper.mapAsDbDocument(airTransaction2));
			docs.add(dBObjectMapper.mapAsDbDocument(airTransaction));
		}

		mongoDbConnection.getCollection(AIR_TRANSACTION_COLLECTION).insertMany(docs);

		System.out.println("Finished migration of airTransactions");
	}

	@SuppressWarnings("unchecked")
	public void migrateAgentContacts() throws JsonProcessingException {

		LOGGER.info("Started agent migration...");
		final List<TblAgent> agents = agentDAOImpl.getAgentList();
		final List<TblAgentConfig> agentConfigs = agentDAOImpl.getAgentConfigList();
		final LdapTemplate template = new LdapTemplate(ldapContextSource);
		final List<AgentInfo> agentInfos = new ArrayList<AgentInfo>();
		
		final List<String> NoLDAPAccount = new ArrayList<String>();
		final List<String> WithLDAPAccount = new ArrayList<String>();

		agents.stream().forEach(a -> {
			agentConfigs.stream().filter(c -> c.getDivision().equalsIgnoreCase(a.getDivision())).forEach(c -> {
				
				String lastName = a.getLastName()==null ? "" : a.getLastName().trim();
				String firstName = a.getFirstName()==null ? "" : a.getFirstName().trim();
						
				final AndFilter searchFilter = new AndFilter();
				searchFilter.and(new EqualsFilter("objectclass", "user"))
							.and(new EqualsFilter("sn", lastName))
							.and(new EqualsFilter("givenName", firstName));

				AgentInfo agentInfo = new AgentInfo();

		        final List<AgentInfo> users = template.search(LdapUtils.emptyLdapName(), searchFilter.encode(), new AttributesMapper<AgentInfo>() {
					@Override
					public AgentInfo mapFromAttributes(Attributes attributes) throws NamingException {
						String uid = (String) attributes.get("sAMAccountName").get();
						if (!StringUtils.isEmpty(uid)) {
							agentInfo.setUid(uid);
							agentInfo.setPhone(c.getPhone());
							agentInfo.setCountryCode(c.getCountryCode());
							agentInfos.add(agentInfo);
							
							String name = a.getLastName() + ", " + a.getFirstName() + " - " + c.getCountryCode();
							WithLDAPAccount.add(name);
						}
						return null;
					}
				});

			});
		});

		List<Document> docs = new ArrayList<>();

		for (AgentInfo info : agentInfos) {
			docs.add(dBObjectMapper.mapAsDbDocument(info));
		}

		mongoDbConnection.getCollection(AGENT_COLLECTION).insertMany(docs);
	}

	private Map<String, Object> mapBookingClasses() {
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("TGCWT", Arrays.asList("T", "K", "S", "V", "W", "L"));
		parameters.put("TGAirline", Arrays.asList("F", "A", "P", "C", "D", "J", "Z", "Y", "B", "M", "H", "Q"));
		parameters.put("MHCWT", Arrays.asList("O", "Q"));
		parameters.put("MHAirline", Arrays.asList("F", "A", "P", "J", "C", "D", "Z", "I", "U", "Y", "B", "H", "K", "M",
				"L", "V", "S", "N", "E", "X", "G"));

		return parameters;
	}

	@SuppressWarnings("unchecked")
	private List<String> formBookingClasses(String airlineCode, String passthroughType) {
		return (List<String>) mapBookingClasses().get(airlineCode + passthroughType);

	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}
}
