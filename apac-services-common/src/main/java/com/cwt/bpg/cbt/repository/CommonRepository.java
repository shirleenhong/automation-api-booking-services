package com.cwt.bpg.cbt.repository;

import com.cwt.bpg.cbt.mongodb.config.MorphiaComponent;
import com.cwt.bpg.cbt.mongodb.mapper.DBObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.mongodb.*;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Key;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.query.Query;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.List;
import java.util.function.Consumer;

/**
 * T ->	class type or class entity used in Morphia
 * <p> D -> data type of unique key in DB
 *
 * @param <T>
 * @param <D>
 */
public class CommonRepository<T, D> {

    private final Class<T> typeClass;
    private final String keyColumn;
    private final String collectionName;

    @Autowired
    protected MorphiaComponent morphia;

    protected CommonRepository(Class<T> typeClass, String keyColumn) {
        this.typeClass = typeClass;
        this.keyColumn = keyColumn;
        this.collectionName = getCollectionName();
    }

    public List<T> getAll() {
        return morphia.getDatastore().createQuery(typeClass).asList();
    }

    /**
     * Basic get based on Key Column
     *
     * @param criteria
     * @return
     */
    public T get(D criteria) {
        return morphia.getDatastore().createQuery(typeClass).field(keyColumn).equal(criteria).get();
    }

    public T put(T object) {
        final D keyValue = getKeyValue(object);
        if (keyValue != null) {
            remove(keyValue);
        }
        final Datastore datastore = morphia.getDatastore();
        Key<T> newKey = datastore.save(object);

        LoggerFactory.getLogger(typeClass).info("Save Result: {}", newKey);
        return object;
    }

    public Iterable<T> putAll(Iterable<T> objects) {
        DBCollection collection = morphia.getDatastore().getCollection(typeClass);
        BulkWriteOperation writeOperation = collection.initializeUnorderedBulkOperation();
        for (T clientGstInfo : objects) {
            mapAndInsertToWriteOperation(clientGstInfo, writeOperation);
        }
        BulkWriteResult result = writeOperation.execute();
        LoggerFactory.getLogger(typeClass)
                .info("Save Result: {} records inserted", result.getInsertedCount());
        return objects;
    }

    public String remove(D keyValue) {
        final Datastore datastore = morphia.getDatastore();
        final Query<T> query = datastore.createQuery(typeClass).field(keyColumn).equal(keyValue);

        WriteResult delete = datastore.delete(query);

        LoggerFactory.getLogger(typeClass).info("Delete Result: {}", delete);

        return delete.getN() > 0 ? keyValue.toString() : "";
    }

    public boolean collectionExists() {
        return morphia.getDatastore().getDB().collectionExists(collectionName);
    }

    @SuppressWarnings("unchecked")
    protected D getKeyValue(T object) {
        D keyValue = null;
        try {
            Field keyField = typeClass.getDeclaredField(keyColumn);
            keyField.setAccessible(true);
            keyValue = (D) keyField.get(object);
        }
        catch (NoSuchFieldException | IllegalAccessException e) {
            LoggerFactory.getLogger(typeClass).error("Unable to get value of key column.", e);
        }
        return keyValue;
    }

    public void identity(Consumer<Object[]> i) {
        i.accept(new Object[] { typeClass, keyColumn });
    }

    private void mapAndInsertToWriteOperation(T object, BulkWriteOperation writeOperation) {
        try {
            BasicDBObject document = DBObjectMapper.mapAsBasicDBObject(getKeyValue(object), object);
            writeOperation.insert(document);
        }
        catch (JsonProcessingException e) {
            LoggerFactory.getLogger(typeClass)
                    .info("An error occurred while converting object to BasicDBObject", e);
        }
    }

    private String getCollectionName() {
        Annotation[] annotations = typeClass.getAnnotations();
        for (Annotation annotation : annotations) {
            if (annotation instanceof Entity) {
                Entity entity = (Entity) annotation;
                return entity.value();
            }
        }
        return null;
    }
}
