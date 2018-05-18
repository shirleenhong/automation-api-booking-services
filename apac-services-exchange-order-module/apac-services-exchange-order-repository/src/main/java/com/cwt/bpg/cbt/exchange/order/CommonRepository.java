package com.cwt.bpg.cbt.exchange.order;

import java.lang.reflect.Field;
import java.util.List;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Key;
import org.mongodb.morphia.query.Query;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.cwt.bpg.cbt.mongodb.config.MorphiaComponent;
import com.mongodb.WriteResult;

class CommonRepository<T> {

	@Autowired
	private MorphiaComponent morphia;

	final private Class<T> typeClass;
	final private String keyColumn;

	CommonRepository(Class<T> typeClass, String keyColumn) {
		this.typeClass = typeClass;
		this.keyColumn = keyColumn;
	}

    public Class<T> getTypeClass()
    {
        return typeClass;
    }

    public String getKeyColumn()
    {
        return keyColumn;
    }

    List<T> getAll() {
		return morphia.getDatastore().createQuery(typeClass).asList();
	}

	T put(T object) {
		final String keyValue = getKeyValue(object);

		if (!keyValue.isEmpty())
        {
            remove(keyValue);

            final Datastore datastore = morphia.getDatastore();
            Key<T> newKey = datastore.save(object);

            LoggerFactory.getLogger(typeClass).info("Save Result: {}", newKey);
        }

		return object;
	}

	String remove(String keyValue) {
		final Datastore datastore = morphia.getDatastore();
		final Query<T> query = datastore.createQuery(typeClass).field(keyColumn).equal(keyValue);

		WriteResult delete = datastore.delete(query);

		LoggerFactory.getLogger(typeClass).info("Delete Result: {}", delete);

		return delete.toString();
	}

	private String getKeyValue(T object) {
		String keyValue = "";
		try {
			Field keyField = typeClass.getDeclaredField(keyColumn);
			keyField.setAccessible(true);
			keyValue = (String) keyField.get(object);
		}
		catch (NoSuchFieldException | IllegalAccessException e) {
			LoggerFactory.getLogger(typeClass).error("Unable to get value of key column.", e);
		}
		return keyValue;
	}
}
