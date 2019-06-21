package com.cwt.bpg.cbt.mongodb.mapper;

import java.io.IOException;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public class DBObjectMapper
{
    private static final ObjectMapper MAPPER;

    static
    {
        final ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.setSerializationInclusion(Include.NON_NULL);
        mapper.registerModules(new CustomModule());
        mapper.enable(MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS);
        
        MAPPER = mapper;
    }

    public static Document mapAsDbDocument(Object object) throws JsonProcessingException
    {
        return Document.parse(MAPPER.writeValueAsString(object));
    }

    public static Document mapAsDbDocument(String id, Object obj) throws JsonProcessingException
    {
        final Document doc = mapAsDbDocument(obj);
        doc.put("_id", id);
        return doc;
    }
    
    public static Document mapAsDbDocument(Integer id, Object obj) throws JsonProcessingException
    {
        final Document doc = mapAsDbDocument(obj);
        doc.put("_id", id);
        return doc;
    }

    public static Document mapAsDbDocumentWithObjectId(ObjectId id, Object obj) throws JsonProcessingException
    {
        final Document doc = mapAsDbDocument(obj);
        doc.put("_id", id);
        return doc;
    }

    public static <T> T mapDbResultToBean(DBObject dbObject, Class<T> beanType) throws IOException
    {
        return dbObject != null ? MAPPER.readValue(dbObject.toString(), beanType) : null;
    }
    
    public static <T> T mapDocumentToBean(Document document, Class<T> beanType) throws IOException
    {
        return document != null ? MAPPER.readValue(document.toJson(), beanType) : null;
    }

    public static <T> T mapDbResultToBean(BasicDBObject dbObject, Class<T> beanType) throws IOException
    {
        return dbObject != null ? MAPPER.readValue(dbObject.toString(), beanType) : null;
    }

    @SuppressWarnings("unchecked")
    public static Document mapDbResultToDocument(DBObject dbObject)
    {
        return dbObject != null ? new Document(dbObject.toMap()) : null;
    }

    public static BasicDBObject mapAsBasicDBObject(Object id, Object object) throws JsonProcessingException
    {
        final BasicDBObject dbObject = BasicDBObject.parse(MAPPER.writeValueAsString(object));
        dbObject.put("_id", id);
        return dbObject;
    }

}
