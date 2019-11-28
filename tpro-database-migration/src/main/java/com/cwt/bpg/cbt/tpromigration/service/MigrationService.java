package com.cwt.bpg.cbt.tpromigration.service;

import static com.mongodb.client.model.Filters.in;
import static com.mongodb.client.model.Projections.excludeId;
import static com.mongodb.client.model.Projections.fields;
import static com.mongodb.client.model.Projections.include;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import javax.naming.NamingException;
import javax.naming.directory.Attributes;

import org.apache.commons.lang3.StringUtils;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
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
import com.cwt.bpg.cbt.exchange.order.model.*;
import com.cwt.bpg.cbt.exchange.order.model.india.AirMiscInfo;
import com.cwt.bpg.cbt.tpromigration.csv.CSVReader;
import com.cwt.bpg.cbt.tpromigration.csv.converter.AirMiscInfoConverter;
import com.cwt.bpg.cbt.tpromigration.csv.converter.AirTransactionConverter;
import com.cwt.bpg.cbt.tpromigration.mongodb.config.MongoDbConnection;
import com.cwt.bpg.cbt.tpromigration.mongodb.mapper.DBObjectMapper;
import com.cwt.bpg.cbt.tpromigration.mssqldb.dao.*;
import com.cwt.bpg.cbt.tpromigration.mssqldb.model.*;
import com.cwt.bpg.cbt.tpromigration.mssqldb.model.Client;
import com.cwt.bpg.cbt.tpromigration.mssqldb.model.ProductList;
import com.cwt.bpg.cbt.tpromigration.mssqldb.model.Vendor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.mongodb.client.FindIterable;

@Service
public class MigrationService
{

    private static final Logger LOGGER = LoggerFactory.getLogger(MigrationService.class);

    private static final String AIRPORT_COLLECTION = "airports";
    private static final String CLIENT_COLLECTION = "clients";
    private static final String AIR_TRANSACTION_COLLECTION = "airTransactions";
    private static final String AIR_CONTRACTS_COLLECTION = "airContracts";
    private static final String AIR_MISC_INFO_COLLECTION = "airMiscInfo";
    private static final String PRODUCTS_COLLECTION = "productList";
    private static final String AGENT_COLLECTION = "agentInfo";
    private static final String BILLING_ENTITIES_COLLECTION = "billingEntities";
    private static final String CLIENT_TRANSACTION_FEE_COLLECTION = "clientTransactionFees";
    public static final String CLIENT_ACCOUNT_NUMBER = "clientAccountNumber";

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
    private AgentDAOImpl agentDAOImpl;

    @Autowired
    private LineDefinitionClientMappingDAO lineDefs;

    @Autowired
    private AirContractDAOImpl airContractDAOImpl;

    @Value("${com.cwt.tpromigration.mongodb.dbuser}")
    private String dbUser;

    @Value("${com.cwt.tpromigration.mongodb.dbpwd}")
    private String dbPwd;

    @Value("${com.cwt.tpromigration.mongodb.dbname}")
    private String dbName;

    private String countryCode;

    public void migrateLineDefClientMapping() throws JsonProcessingException
    {
        List<LineDefinitionClientMapping> lineDefinitionClientMappings = lineDefs.getLineDefinitionClientMapping();

        List<Document> docs = new ArrayList<>();

        for (LineDefinitionClientMapping lineDefinitionClientMapping : lineDefinitionClientMappings)
        {
            docs.add(dBObjectMapper.mapAsDbDocument(lineDefinitionClientMapping));
        }

        mongoDbConnection.getCollection(BILLING_ENTITIES_COLLECTION).insertMany(docs);
    }

    @SuppressWarnings("unchecked")
    public void migrateProductList() throws JsonProcessingException
    {
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

            if (!ObjectUtils.isEmpty(contactInfoList))
            {

                List<ContactInfo> contactList = new ArrayList<>();

                contactInfoList.forEach(ci -> {
                    if (vendor.getCode().equals(ci.getVendorNumber()))
                    {
                        setMigratedContactInfo(contactList, ci.getType(), ci.getDetail(), ci.isPreferred());
                    }

                });
                LOGGER.info("size of contact info saved in vendor " + contactList.size());
                vendor.setContactInfo(contactList);
            }
            else
            {
                List<ContactInfo> contactList = new ArrayList<>();

                if (!ObjectUtils.isEmpty(vendor.getEmail()))
                {
                    setMigratedContactInfo(contactList, ContactInfoType.EMAIL, vendor.getEmail(), true);
                }
                if (!ObjectUtils.isEmpty(vendor.getFaxNumber()))
                {
                    setMigratedContactInfo(contactList, ContactInfoType.FAX, vendor.getFaxNumber(), true);
                }
                if (!ObjectUtils.isEmpty(vendor.getContactNo()))
                {
                    setMigratedContactInfo(contactList, ContactInfoType.PHONE, vendor.getContactNo(), true);
                }
                LOGGER.info("size of contact info saved in vendor " + contactList.size());
                vendor.setContactInfo(contactList);
            }

            vendor.setEmail(null);
            vendor.setFaxNumber(null);
            vendor.setContactNo(null);


            productCodes.forEach(productCode -> {
                if (productsMap.get(productCode) != null)
                {
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
            String productCode)
    {

        Vendor vendorClone = new Vendor();
        BeanUtils.copyProperties(vendorClone, vendor);

        for (MerchantFeeAbsorb mf : noMerchantFeeList)
        {
            if (productCode.equals(mf.getProductCode()) && vendorClone.getCode().equals(mf.getVendorNumber()))
            {
                vendorClone.setMerchantFeeAbsorb(true);
                break;
            }
        }
        return vendorClone;
    }

    private void setMigratedContactInfo(List<ContactInfo> contactList, ContactInfoType type, String detail,
            Boolean preferred)
    {

        ContactInfo contactInfo = new ContactInfo();
        contactInfo.setType(type);
        contactInfo.setDetail(detail);
        contactInfo.setPreferred(preferred);
        contactList.add(contactInfo);
    }

    public void migrateAirports() throws JsonProcessingException
    {
        List<Airport> airports = airportDAO.getAirports();

        List<Document> docs = new ArrayList<>();

        for (Airport airport : airports)
        {
            docs.add(dBObjectMapper.mapAsDbDocument(airport.getCode(), airport));
        }

        mongoDbConnection.getCollection(AIRPORT_COLLECTION).insertMany(docs);
    }

    public void migrateMerchantFees() throws JsonProcessingException
    {

        LOGGER.info("Started merchant fee migration...");

        List<MerchantFee> merchantFees = clientMerchantFeeDAO.listMerchantFees(countryCode);

        for (MerchantFee merchantFee : merchantFees)
        {
            mongoDbConnection.getCollection("clientMerchantFee").insertOne(dBObjectMapper.mapAsDbDocument(merchantFee));
        }

        LOGGER.info("End of merchant fee migration...");
    }

    public void migrateAirlineRules() throws JsonProcessingException
    {
        LOGGER.info("Started airline rule migration...");
        
        List<AirlineRule> rules = airlineRuleDAO.list();
        List<Document> docs = new ArrayList<>();
        for (AirlineRule rule : rules)
        {
            docs.add(dBObjectMapper.mapAsDbDocument(rule.getCode(), rule));
        }

        mongoDbConnection.getCollection("airlineRules").insertMany(docs);
        LOGGER.info("End of airline rule migration...");
    }

    public void migrateClients() throws JsonProcessingException
    {
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
        List<Client> clientsFromBilling = clientDAO.getClientsFromBilling();
        List<Client> clientsGstin = clientDAO.getClientsWithGstin();
        List<AirVariables> airVariables = clientDAO.getAirVariables();

        Client defaultClient = new Client();
        defaultClient.setClientId(-1);
        defaultClient.setApplyMfBank(false);
        defaultClient.setApplyMfCc(false);
        clients.add(defaultClient);
        clients.removeAll(clientsFromBilling);
        clients.addAll(clientsFromBilling);

        updateClients(clients, clientsGstin, productsMap, ccsMap, banksMap, clientPricingMaps, airVariables,
                transactionFeeByPNR, transactionFeeByCoupon, transactionFeeByTicket);

        List<String> existingClientList = new ArrayList<>();
        List<String> allClients = clients.stream().map(client -> client.getClientAccountNumber()).collect(Collectors.toList());
        FindIterable<Document> existingClients = mongoDbConnection.getCollection(CLIENT_COLLECTION)
                    .find(in(CLIENT_ACCOUNT_NUMBER, allClients))
                    .projection(fields(include(CLIENT_ACCOUNT_NUMBER), excludeId()));

        for (Document doc : existingClients) {
            existingClientList.add(doc.getString(CLIENT_ACCOUNT_NUMBER));
        }

        List<Document> docs = new ArrayList<>();

        for (Client client : clients)
        {
            if(!existingClientList.contains(client.getClientAccountNumber()))
            {
                LOGGER.info("Adding "+ client.getClientAccountNumber());
                docs.add(dBObjectMapper.mapAsDbDocument(client));
            }
        }

        if(!docs.isEmpty()) {
            mongoDbConnection.getCollection(CLIENT_COLLECTION).insertMany(docs);
        }

        LOGGER.info("End of clients migration...");
    }

    public void migrateClientTransactionFees() throws JsonProcessingException
    {
        LOGGER.info("Started client transaction fees migration...");
        List<InClientTransactionFee> clientTransactionFees = clientDAO.getClientTransactionFees();

        List<Document> docs = new ArrayList<>();
        for (InClientTransactionFee clientTransactionFee : clientTransactionFees)
        {
            docs.add(dBObjectMapper.mapAsDbDocument(clientTransactionFee.getClientAccountNumber(),
                    clientTransactionFee));
        }

        mongoDbConnection.getCollection(CLIENT_TRANSACTION_FEE_COLLECTION).insertMany(docs);
        LOGGER.info("End of client transaction fees migration...");
    }

    private Map<Integer, List<TransactionFee>> getTransactionFeesMap(List<TransactionFee> transactionFees)
    {
        Map<Integer, List<TransactionFee>> transactionFeesMap = new HashMap<>();
        int previousGroupId = 0;
        List<TransactionFee> groupedTransactionFees = null;
        for (TransactionFee transactionFee : transactionFees)
        {
            if (previousGroupId != transactionFee.getFeeId())
            {
                groupedTransactionFees = new ArrayList<>();
            }
            groupedTransactionFees.add(transactionFee);
            previousGroupId = transactionFee.getFeeId();
            transactionFeesMap.put(previousGroupId, groupedTransactionFees);
        }
        return transactionFeesMap;
    }

    private Map<Integer, Map<Integer, ClientPricing>> getClientPricingMaps(List<ClientPricing> clientPricings)
    {
        Map<Integer, Map<Integer, ClientPricing>> result = new HashMap<>();
        int previousCmpId = 0;
        Integer i = 0;
        Map<Integer, ClientPricing> clientPricingMap = null;
        for (ClientPricing clientPricing : clientPricings)
        {
            if (previousCmpId != clientPricing.getCmpid())
            {
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

    private Map<Integer, List<ProductMerchantFee>> getProductMap(List<ProductMerchantFee> items)
    {
        Map<Integer, List<ProductMerchantFee>> result = new HashMap<>();

        for (ProductMerchantFee item : items)
        {

            if (result.containsKey(item.getClientId()))
            {
                result.get(item.getClientId()).add(item);
            }
            else
            {
                List<ProductMerchantFee> list = new ArrayList<>();
                list.add(item);
                result.put(item.getClientId(), list);
            }
        }
        return result;
    }

    private Map<Integer, List<CreditCardVendor>> getVendoMap(List<CreditCardVendor> items)
    {
        Map<Integer, List<CreditCardVendor>> result = new HashMap<>();

        for (CreditCardVendor item : items)
        {

            if (result.containsKey(item.getClientId()))
            {
                result.get(item.getClientId()).add(item);
            }
            else
            {
                List<CreditCardVendor> list = new ArrayList<>();
                list.add(item);
                result.put(item.getClientId(), list);
            }
        }
        return result;
    }

    private Map<Integer, List<Bank>> getBankMap(List<Bank> items)
    {
        Map<Integer, List<Bank>> result = new HashMap<>();

        for (Bank item : items)
        {

            if (result.containsKey(item.getClientId()))
            {
                result.get(item.getClientId()).add(item);
            }
            else
            {
                List<Bank> list = new ArrayList<>();
                list.add(item);
                result.put(item.getClientId(), list);
            }
        }
        return result;
    }

    private Collection< ? extends Client> updateClients(List<Client> clients, List<Client> clientsGstin,
            Map<Integer, List<ProductMerchantFee>> productsMap, Map<Integer, List<CreditCardVendor>> vendorsMap,
            Map<Integer, List<Bank>> banksMap, Map<Integer, Map<Integer, ClientPricing>> clientPricingMaps,
            List<AirVariables> airVariables, Map<Integer, List<TransactionFee>> transactionFeeByPNR,
            Map<Integer, List<TransactionFee>> transactionFeeByCoupon,
            Map<Integer, List<TransactionFee>> transactionFeeByTicket)
    {

        for (Client client : clients)
        {

            if (productsMap.containsKey(client.getClientId()) || vendorsMap.containsKey(client.getClientId())
                    || banksMap.containsKey(client.getClientId()))
            {

                client.setMfProducts(productsMap.get(client.getClientId()));
                client.setMfCcs(vendorsMap.get(client.getClientId()));
                client.setMfBanks(banksMap.get(client.getClientId()));
            }
            List<ClientPricing> clientPricings = new ArrayList<>();
            if (clientPricingMaps.containsKey(client.getCmpid()))
            {
                Map<Integer, ClientPricing> clientPricingMap = clientPricingMaps.get(client.getCmpid());

                for (ClientPricing clientPricing : clientPricingMap.values())
                {

                    for (AirVariables airVariable : airVariables)
                    {
                        if (clientPricing.getFieldId().equals(airVariable.getFieldId()))
                        {
                            clientPricing.setFeeName(airVariable.getFieldName());
                            break;
                        }
                    }

                    if (clientPricing.getFieldId() == 5)
                    {

                        String feeOption = clientPricing.getFeeOption();
                        if ("P".equals(feeOption))
                        {
                            clientPricing.setTransactionFees(transactionFeeByPNR.get(clientPricing.getValue()));
                        }
                        else if ("C".equals(feeOption))
                        {
                            clientPricing.setTransactionFees(transactionFeeByCoupon.get(clientPricing.getValue()));
                        }
                        else if ("T".equals(feeOption))
                        {
                            clientPricing.setTransactionFees(transactionFeeByTicket.get(clientPricing.getValue()));
                        }
                    }
                    clientPricings.add(clientPricing);
                }
                client.setClientPricings(clientPricings);
            }

            for (Client clientGstin : clientsGstin)
            {
                if (client.getClientAccountNumber() != null && clientGstin.getClientAccountNumber() != null
                        && clientGstin.getClientAccountNumber().equals(client.getClientAccountNumber()))
                {
                    client.setGstin(clientGstin.getGstin());
                    break;
                }
            }
        }

        return clients;
    }

    public void migrateRemarks() throws JsonProcessingException
    {

        for (Remark remark : remarkDAO.getRemarks())
        {
            remark.setCountryCode(countryCode);
            mongoDbConnection.getCollection("remarkList").insertOne(dBObjectMapper.mapAsDbDocument(remark));
        }
    }

    public void migrateAirContracts() throws JsonProcessingException
    {

        LOGGER.info("Started client air contracts migration...");
        List<AirContract> airContracts = airContractDAOImpl.getList();
        List<Document> docs = new ArrayList<>();
        for (AirContract airContract : airContracts)
        {
            airContract.setCountryCode(countryCode);
            docs.add(dBObjectMapper.mapAsDbDocument(airContract));
        }

        mongoDbConnection.getCollection(AIR_CONTRACTS_COLLECTION).insertMany(docs);

        LOGGER.info("End of client air contracts migration...");
    }

    public void migrateAirMiscInfo() throws JsonProcessingException
    {

        LOGGER.info("Started air misc info migration...");
        List<AirMiscInfo> airMiscInfoList = clientDAO.getAirMiscInfo();
        List<Document> docs = new ArrayList<>();
        for (AirMiscInfo airMiscInfo : airMiscInfoList)
        {
            docs.add(dBObjectMapper.mapAsDbDocument(airMiscInfo));
        }

        mongoDbConnection.getCollection(AIR_MISC_INFO_COLLECTION).insertMany(docs);
        LOGGER.info("End of air misc info migration...");
    }

    public void migrateAirMiscInfoMerged(final String wave) throws IOException
    {
        LOGGER.info("Started air misc info migration with wave {}", wave);
        final List<AirMiscInfo> airMiscInfoList = clientDAO.getAirMiscInfo();
        final List<Document> docs = new ArrayList<>();
        airMiscInfoList.forEach(e -> {
            try
            {
                docs.add(dBObjectMapper.mapAsDbDocument(e));
            }
            catch (IOException ex)
            {
                LOGGER.error(ex.getMessage(), e);
            }
        });

        final List<AirMiscInfo> airMiscInfoCSVList = new CSVReader().parse("data/" + wave + "/airMiscInfo.csv", new AirMiscInfoConverter());
        airMiscInfoCSVList.forEach(e -> {
            try
            {
                docs.add(dBObjectMapper.mapAsDbDocument(e));
            }
            catch (IOException ex)
            {
                LOGGER.error(ex.getMessage(), e);
            }
        });

        mongoDbConnection.getCollection(AIR_MISC_INFO_COLLECTION).insertMany(docs);
        LOGGER.info("End of air misc info migration...");
    }

    public void migratePassthroughs(final String wave) throws IOException
    {
        LOGGER.info("Started air transactions migration with wave {}", wave);
        final List<Document> docs = new ArrayList<>();
        final List<AirTransaction> airTransCSVList = new CSVReader().parse("data/" + wave + "/airTransactions.csv", new AirTransactionConverter());
        airTransCSVList.forEach(e -> {
            try
            {
                docs.add(dBObjectMapper.mapAsDbDocument(e));
            }
            catch (IOException ex)
            {
                LOGGER.error(ex.getMessage(), e);
            }
        });

        mongoDbConnection.getCollection(AIR_TRANSACTION_COLLECTION).insertMany(docs);
        LOGGER.info("Finished migration of airTransactions");
    }

    public void migrateAgentContacts() throws JsonProcessingException
    {
        LOGGER.info("Started agent migration...");
        final List<TblAgent> agents = agentDAOImpl.getAgentList();
        final List<TblAgentConfig> agentConfigs = agentDAOImpl.getAgentConfigList();
        final LdapTemplate template = new LdapTemplate(ldapContextSource);
        final List<AgentInfo> agentInfos = new ArrayList<>();

        agents.stream().forEach(a -> {
            agentConfigs.stream().filter(c -> c.getDivision().equalsIgnoreCase(a.getDivision())).forEach(c -> {

                String lastName = a.getLastName() == null ? "" : a.getLastName().trim();
                String firstName = a.getFirstName() == null ? "" : a.getFirstName().trim();

                final AndFilter searchFilter = new AndFilter();
                searchFilter.and(new EqualsFilter("objectclass", "user"))
                        .and(new EqualsFilter("sn", lastName))
                        .and(new EqualsFilter("givenName", firstName));

                AgentInfo agentInfo = new AgentInfo();

                template.search(LdapUtils.emptyLdapName(), searchFilter.encode(), new AttributesMapper<AgentInfo>()
                {
                    @Override
                    public AgentInfo mapFromAttributes(Attributes attributes) throws NamingException
                    {
                        String uid = (String) attributes.get("sAMAccountName").get();
                        if (!StringUtils.isEmpty(uid))
                        {
                            agentInfo.setUid(uid);
                            agentInfo.setPhone(c.getPhone());
                            agentInfo.setCountryCode(c.getCountryCode());
                            agentInfos.add(agentInfo);
                        }
                        return null;
                    }
                });

            });
        });

        List<Document> docs = new ArrayList<>();

        for (AgentInfo info : agentInfos)
        {
            docs.add(dBObjectMapper.mapAsDbDocument(info));
        }

        mongoDbConnection.getCollection(AGENT_COLLECTION).insertMany(docs);
    }

    public String getCountryCode()
    {
        return countryCode;
    }

    public void setCountryCode(String countryCode)
    {
        this.countryCode = countryCode;
    }
}
