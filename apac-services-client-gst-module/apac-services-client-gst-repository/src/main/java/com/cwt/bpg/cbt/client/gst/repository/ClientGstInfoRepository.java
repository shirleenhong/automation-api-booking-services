package com.cwt.bpg.cbt.client.gst.repository;

import com.cwt.bpg.cbt.client.gst.model.ClientGstInfo;
import com.cwt.bpg.cbt.repository.CommonRepository;
import com.mongodb.DBCollection;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Repository
public class ClientGstInfoRepository extends CommonRepository<ClientGstInfo, String> {

    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("MMddyyHHmmss");

    private static final String KEY_COLUMN = "gstin";

    public ClientGstInfoRepository() {
        super(ClientGstInfo.class, KEY_COLUMN);
    }

    public void backupCollection() {
        if(collectionExists()) {
            LocalDateTime currentDate = LocalDateTime.now();
            DBCollection collection = morphia.getDatastore().getCollection(ClientGstInfo.class);
            collection.rename(collection.getName() + "_" + currentDate.format(DATE_FORMAT));
        }
    }
}
