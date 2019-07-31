package com.cwt.bpg.cbt.tpromigration.mongodb.config;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.cwt.bpg.cbt.tpromigration.encryptor.Encryptor;
import com.cwt.bpg.cbt.tpromigration.mongodb.config.util.MongoSSLCertificateUtility;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Indexes;

@Lazy
@Component
public class MongoDbConnection
{
    private static final Logger LOGGER = LoggerFactory.getLogger(MongoDbConnection.class);

    @Value("${com.cwt.tpromigration.mongodb.dbuser}")
    private String dbUser;

    @Value("${com.cwt.tpromigration.mongodb.dbpwd}")
    private String dbPwd;

    @Value("${com.cwt.tpromigration.mongodb.dbname}")
    private String dbName;

    @Value("${com.cwt.tpromigration.mongodb.dbAddresses}")
    private String dbAddresses;

    @Value("${com.cwt.tpromigration.mongodb.database.sslenabled}")
    private Boolean sslEnabled;

    @Value("${com.cwt.tpromigration.mongodb.database.invalidhostnameallowed}")
    private Boolean invalidHostNameAllowed;

    @Value("${com.cwt.tpromigration.encryptor.key}")
    private String secretKey;

    private MongoClient mongoClient;

    private MongoDatabase database;
    
    @Autowired
    private Encryptor encryptor;

    private ConcurrentHashMap<String, String> indexMap;

    public MongoDatabase getDatabase()
    {
        return this.database;
    }

    @PostConstruct
    public void init()
    {

        LOGGER.info("Initializing mongodb connection");
        try
        {
            char[] pwd = encryptor.decrypt(this.dbPwd).toCharArray();
            String mongoDBName = this.dbName;
            MongoCredential credential = MongoCredential.createCredential(encryptor.decrypt(this.dbUser), mongoDBName, pwd);

            List<MongoCredential> credentials = new ArrayList<>();
            credentials.add(credential);
            MongoClientOptions.Builder options = new MongoClientOptions.Builder().sslEnabled(sslEnabled)
                    .sslInvalidHostNameAllowed(invalidHostNameAllowed)
                    .socketFactory(MongoSSLCertificateUtility.mongoDbSocketFactory());
            
            List<ServerAddress> hosts = new ArrayList<>();

            String[] adresses = this.dbAddresses.split(",");

            for (String address : adresses)
            {
                hosts.add(new ServerAddress(address));
            }

            mongoClient = new MongoClient(hosts, credential, options.build());
            this.database = mongoClient.getDatabase(mongoDBName);
            LOGGER.info("Mongodb connection initialized");
        }
        catch (Exception e)
        {
            LOGGER.error("Exception while creating mongodb connection", e);
        }
    }

    @PreDestroy
    public void preDestroy()
    {
        this.mongoClient.close();
    }

    public boolean addIndex(final MongoCollection<Document> collection, String field)
    {
        final String indexMapKey = buildIndexMapKey(collection.getNamespace().getCollectionName(), field);

        if (getIndexMap().containsKey(indexMapKey))
        {
            return true;
        }

        try
        {
            final String indexName = collection.createIndex(Indexes.text(field));
            getIndexMap().put(indexMapKey, indexName);
            return true;
        }
        catch (Exception e)
        {
            LOGGER.error("Exception while creating an index", e);
        }

        return false;

    }

    private ConcurrentHashMap<String, String> getIndexMap()
    {
        if (this.indexMap == null)
        {
            this.indexMap = new ConcurrentHashMap<>(4, 0.9f);
        }

        return this.indexMap;
    }

    private String buildIndexMapKey(String collectionName, String field)
    {
        return new StringBuilder(collectionName).append("-").append(field).toString();
    }

	public MongoCollection getCollection(String collection) {
		return this.getDatabase().getCollection(collection);
	}
}
