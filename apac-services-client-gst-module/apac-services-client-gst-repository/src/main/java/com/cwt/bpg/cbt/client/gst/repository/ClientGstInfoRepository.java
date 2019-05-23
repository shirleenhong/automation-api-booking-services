package com.cwt.bpg.cbt.client.gst.repository;

import com.cwt.bpg.cbt.client.gst.model.ClientGstInfo;
import com.cwt.bpg.cbt.mongodb.mapper.DBObjectMapper;
import com.cwt.bpg.cbt.repository.CommonRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.mongodb.BasicDBObject;
import com.mongodb.BulkWriteOperation;
import com.mongodb.BulkWriteResult;
import com.mongodb.DBCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Repository
public class ClientGstInfoRepository extends CommonRepository<ClientGstInfo, String>
{

    public static final String KEY_COLUMN = "gstin";
    private static final Logger LOGGER = LoggerFactory.getLogger(ClientGstInfoRepository.class);
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("MMddyy");

    public ClientGstInfoRepository()
    {
        super(ClientGstInfo.class, KEY_COLUMN);
    }

    public void backupCollection()
    {
        LocalDate currentDate = LocalDate.now();
        DBCollection collection = morphia.getDatastore().getCollection(ClientGstInfo.class);
        collection.rename(collection.getName() + "_" + currentDate.format(DATE_FORMAT));
    }

    public List<ClientGstInfo> putAll(List<ClientGstInfo> objects)
    {
        DBCollection collection = morphia.getDatastore().getCollection(ClientGstInfo.class);
        BulkWriteOperation writeOperation = collection.initializeUnorderedBulkOperation();
        for (ClientGstInfo clientGstInfo : objects)
        {
            mapAndInsertToWriteOperation(clientGstInfo, writeOperation);
        }
        BulkWriteResult result = writeOperation.execute();
        LOGGER.info("Save Result: {} records inserted", result.getInsertedCount());
        return objects;
    }

    private void mapAndInsertToWriteOperation(ClientGstInfo clientGstInfo, BulkWriteOperation writeOperation)
    {
        try
        {
            BasicDBObject document = DBObjectMapper.mapAsBasicDBObject(clientGstInfo.getGstin(), clientGstInfo);
            writeOperation.insert(document);
        }
        catch (JsonProcessingException e)
        {
            LOGGER.info("An error occurred while converting object to BasicDBObject", e);
        }
    }
}
