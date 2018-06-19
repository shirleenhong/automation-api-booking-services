package com.cwt.bpg.cbt.exchange.order;

import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cwt.bpg.cbt.exchange.order.model.SequenceNumber;
import com.cwt.bpg.cbt.mongodb.config.MorphiaComponent;

@Repository
public class SequenceNumberRepository {

	private static final Logger LOGGER = LoggerFactory.getLogger(SequenceNumberRepository.class);
	
	@Autowired
	private MorphiaComponent morphia;
	
	private int maxRetryCount = 3; 
	
	//Draft - not final yet
	public int getSequenceNumber() {
		
		Query<SequenceNumber> query = morphia.getDatastore()
				.createQuery(SequenceNumber.class)
				.field("id").equal("count")
				.disableValidation();
		
		UpdateOperations<SequenceNumber> operations = morphia.getDatastore()
				.createUpdateOperations(SequenceNumber.class);
		
		operations.inc("value", 1);
		
		SequenceNumber result = morphia.getDatastore().findAndModify(query, operations);
				
		return result.getValue();
	}

}
