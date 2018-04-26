package com.cwt.bpg.cbt.mongodb.config;

import javax.annotation.PostConstruct;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MorphiaComponent {
	
	private Morphia morphia;
	
	private Datastore datastore;
	
    @Value("${com.cwt.mongodb.dbname}")
    private String dbName;
	
	@Autowired
	private MongoDbConnection mongodbConnection;
	
	@PostConstruct
	public void init() {
		morphia = new Morphia();
		
		// TODO: make configurable?
		morphia.mapPackage("com.cwt.bpg.cbt.exchange.order.model", true);
		
		datastore = morphia.createDatastore(mongodbConnection.getMongoClient(), dbName);
		datastore.ensureIndexes();
		
	}
	
	public Datastore getDatastore() {
		return datastore;
	}
}
