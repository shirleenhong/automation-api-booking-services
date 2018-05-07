package com.cwt.bpg.cbt.exchange.order;

import java.util.List;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Key;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;
import org.mongodb.morphia.query.UpdateResults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cwt.bpg.cbt.exchange.order.model.Insurance;
import com.cwt.bpg.cbt.mongodb.config.MorphiaComponent;
import com.mongodb.WriteResult;

@Repository
public class InsuranceRepository {

	private static final String TYPE = "type";
	
	private static final String COMMISSION = "commission";

	private static final Logger LOGGER = LoggerFactory.getLogger(InsuranceRepository.class);
	
	@Autowired
	private MorphiaComponent morphia;
	
	public List<Insurance> getAll() {
		return morphia.getDatastore().createQuery(Insurance.class)
				.asList();
	}
	
	public Insurance putInsurance(Insurance insurance) {
		final Datastore datastore = morphia.getDatastore();
		final Query<Insurance> qryInsurance = datastore.createQuery(Insurance.class)
				.field(TYPE) 
				.equal(insurance.getType());
		
		if (qryInsurance.count() > 0) {
			UpdateOperations<Insurance> updateOperation = datastore.createUpdateOperations(Insurance.class)
					.set(TYPE, insurance.getType())
					.set(COMMISSION, insurance.getCommission());
			
			UpdateResults update = datastore.update(qryInsurance, updateOperation);
			LOGGER.info("Update Result: {}", update.toString());
		}
		else {
			Key<Insurance> key = datastore.save(insurance);
			LOGGER.info("Save Result: {}", key.toString());
		}
		
		return insurance;
	}

	public Insurance remove(Insurance insurance) {
		final Datastore datastore = morphia.getDatastore();
		final Query<Insurance> qryInsurance = datastore.createQuery(Insurance.class)
				.field(TYPE) 
				.equal(insurance.getType());
		WriteResult delete = datastore.delete(qryInsurance);
		LOGGER.info("Delete Result: {}", delete.toString());
		
		return insurance;
	}
}
