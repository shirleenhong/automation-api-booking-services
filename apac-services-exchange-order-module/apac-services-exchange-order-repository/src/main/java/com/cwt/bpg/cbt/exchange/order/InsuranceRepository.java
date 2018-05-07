package com.cwt.bpg.cbt.exchange.order;

import java.util.List;

import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Key;
import org.mongodb.morphia.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cwt.bpg.cbt.exchange.order.model.Insurance;
import com.cwt.bpg.cbt.mongodb.config.MorphiaComponent;
import com.mongodb.WriteResult;

@Repository
public class InsuranceRepository {

	private static final Logger LOGGER = LoggerFactory.getLogger(InsuranceRepository.class);
	
	@Autowired
	private MorphiaComponent morphia;
	
	public List<Insurance> getAll() {
		return morphia.getDatastore().createQuery(Insurance.class)
				.asList();
	}
	
	public Insurance putInsurance(Insurance insurance) {
		final Datastore datastore = morphia.getDatastore();
		insurance.setId(new ObjectId(insurance.getType().getBytes()));
		Key<Insurance> key = datastore.save(insurance);
		LOGGER.info("Save Result: {}", key.toString());
		return insurance;
	}

	public String remove(String type) {
		final Datastore datastore = morphia.getDatastore();
		
		final Query<Insurance> qryInsurance = datastore.createQuery(Insurance.class)
								.field("type") 
								.equal(type);
		
		WriteResult delete = datastore.delete(qryInsurance);
		LOGGER.info("Delete Result: {}", delete.toString());
		
		return delete.toString();
	}
}
