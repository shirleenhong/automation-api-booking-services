package com.cwt.bpg.cbt.exchange.order;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.UpdateOperations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cwt.bpg.cbt.exchange.order.model.ExchangeOrder;
import com.cwt.bpg.cbt.mongodb.config.MorphiaComponent;

@Repository
public class ExchangeOrderRepository {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ExchangeOrderRepository.class);
	
	@Autowired
	private MorphiaComponent morphia;
	
	public ExchangeOrder saveOrUpdate(ExchangeOrder eo) {
		LOGGER.info("Save or update: Exchange order, [{}]", eo.getSequenceNumber());
		final Datastore datastore = morphia.getDatastore();
		UpdateOperations<ExchangeOrder> ops = datastore.createUpdateOperations(ExchangeOrder.class);
		datastore.update(eo, ops);
		return eo;
	}
}
