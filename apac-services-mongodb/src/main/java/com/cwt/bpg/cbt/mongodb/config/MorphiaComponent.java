package com.cwt.bpg.cbt.mongodb.config;

import javax.annotation.PostConstruct;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MorphiaComponent
{

    private Datastore datastore;

    @Value("${com.cwt.mongodb.dbname}")
    private String dbName;

    @Autowired
    private MongoDbConnection mongodbConnection;

    @PostConstruct
    public void init()
    {
        Morphia morphia = new Morphia();

<<<<<<< HEAD
=======
        // morphia.getMapper().getConverters().removeConverter(new EnumConverter());

>>>>>>> develop
        morphia.mapPackage("com.cwt.bpg.cbt.exchange.order.model", true);
        morphia.mapPackage("com.cwt.bpg.cbt.air.transaction.model", true);

        datastore = morphia.createDatastore(mongodbConnection.getMongoClient(), dbName);
        datastore.ensureIndexes();
    }

    public Datastore getDatastore()
    {
        return datastore;
    }
}
