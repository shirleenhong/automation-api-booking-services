package com.cwt.bpg.cbt.exchange.order;

import java.lang.reflect.Field;
import java.util.List;
import java.util.function.Consumer;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Key;
import org.mongodb.morphia.query.Query;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.cwt.bpg.cbt.mongodb.config.MorphiaComponent;
import com.mongodb.WriteResult;

class CommonRepository<T, DataType> {

	//TODO Adjust to support get by Id and get by field?
	@Autowired
	protected MorphiaComponent morphia;

	private final Class<T> typeClass;
	private final String keyColumn;

	CommonRepository(Class<T> typeClass, String keyColumn) {
		this.typeClass = typeClass;
		this.keyColumn = keyColumn;
	}

    List<T> getAll() {
		return morphia.getDatastore().createQuery(typeClass).asList();
	}

	T put(T object) {
		final DataType keyValue = getKeyValue(object);

		if (keyValue != null)
        {
            remove(keyValue);

            final Datastore datastore = morphia.getDatastore();
            Key<T> newKey = datastore.save(object);

            LoggerFactory.getLogger(typeClass).info("Save Result: {}", newKey);
        }

		return object;
	}

	String remove(DataType keyValue) {
		final Datastore datastore = morphia.getDatastore();
		final Query<T> query = datastore.createQuery(typeClass).field(keyColumn).equal(keyValue);

		WriteResult delete = datastore.delete(query);

		LoggerFactory.getLogger(typeClass).info("Delete Result: {}", delete);

		return delete.toString();
	}

	@SuppressWarnings("unchecked")
	private DataType getKeyValue(T object) {
		DataType keyValue = null;
		try {
			Field keyField = typeClass.getDeclaredField(keyColumn);
			keyField.setAccessible(true);
			keyValue = (DataType) keyField.get(object);
		}
		catch (NoSuchFieldException | IllegalAccessException e) {
			LoggerFactory.getLogger(typeClass).error("Unable to get value of key column.", e);
		}
		return keyValue;
	}
	
	public void identity(Consumer<Object[]> i) {
		i.accept(new Object[] { typeClass, keyColumn });
	}
}
