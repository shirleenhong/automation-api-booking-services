package com.cwt.bpg.cbt.mongodb.config.mapper;

import java.io.IOException;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

@Component
public class DBObjectMapper
{
    private ObjectMapper mapper;

    public DBObjectMapper()
    {
        final ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.setSerializationInclusion(Include.NON_NULL);
        mapper.registerModules(new CustomModule());
        this.mapper = mapper;
    }

    public Document mapAsDbDocument(Object object) throws JsonProcessingException
    {
        return Document.parse(mapper.writeValueAsString(object));
    }

    public Document mapAsDbDocument(String id, Object obj) throws JsonProcessingException
    {
        final Document doc = mapAsDbDocument(obj);
        doc.put("_id", id);
        return doc;
    }

    public Document mapAsDbDocumentWithObjectId(ObjectId id, Object obj) throws JsonProcessingException
    {
        final Document doc = mapAsDbDocument(obj);
        doc.put("_id", id);
        return doc;
    }

    public <T> T mapDbResultToBean(DBObject dbObject, Class<T> beanType) throws IOException
    {
        return dbObject != null ? mapper.readValue(dbObject.toString(), beanType) : null;
    }
    
    public <T> T mapDocumentToBean(Document document, Class<T> beanType) throws IOException
    {
        return document != null ? mapper.readValue(document.toJson(), beanType) : null;
    }

    public <T> T mapDbResultToBean(BasicDBObject dbObject, Class<T> beanType) throws IOException
    {
        return dbObject != null ? mapper.readValue(dbObject.toString(), beanType) : null;
    }

    @SuppressWarnings("unchecked")
    public Document mapDbResultToDocument(DBObject dbObject)
    {
        return dbObject != null ? new Document(dbObject.toMap()) : null;
    }

}
