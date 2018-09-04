package com.cwt.bpg.cbt.repository;

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

/**
 * T ->	class type or class entity used in Morphia
 * <p> D -> data type of unique key in DB
 * 
 * @param <T> 
 * @param <D>
 */
public class CommonRepository<T, D> {

	@Autowired
	protected MorphiaComponent morphia;

	private final Class<T> typeClass;
	private final String keyColumn;

	protected CommonRepository(Class<T> typeClass, String keyColumn) {
		this.typeClass = typeClass;
		this.keyColumn = keyColumn;
	}

    public List<T> getAll() {
		return morphia.getDatastore().createQuery(typeClass).asList();
	}
    
    /**
     * Basic get based on Key Column
     * @param criteria
     * @return
     */
    public T get(D criteria) {
    	return morphia.getDatastore().createQuery(typeClass)
    			.field(keyColumn)
    			.equal(criteria)
    			.get();
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

	public String remove(D keyValue) {
		final Datastore datastore = morphia.getDatastore();
		final Query<T> query = datastore.createQuery(typeClass).field(keyColumn).equal(keyValue);

		WriteResult delete = datastore.delete(query);

		LoggerFactory.getLogger(typeClass).info("Delete Result: {}", delete);

		return delete.getN() > 0 ? keyValue.toString() : "";
	}

	@SuppressWarnings("unchecked")
	private D getKeyValue(T object) {
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
}
