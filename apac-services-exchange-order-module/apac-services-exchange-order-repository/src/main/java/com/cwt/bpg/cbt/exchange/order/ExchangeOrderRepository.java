package com.cwt.bpg.cbt.exchange.order;

import java.util.List;

import org.mongodb.morphia.query.Sort;
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
	
	public ExchangeOrder save(ExchangeOrder eo) {		
		morphia.getDatastore().save(eo);
		LOGGER.info("Save: Exchange order, [{}]", eo.getEoNumber());
		return eo;
	}
	
	public ExchangeOrder update(ExchangeOrder eo) {
		morphia.getDatastore().merge(eo);
		LOGGER.info("Update: Exchange order, [{}]", eo.getEoNumber());
		return eo;
	}
	
	public ExchangeOrder getExchangeOrder(String eoNumber) {
		return morphia.getDatastore().createQuery(ExchangeOrder.class)
			.field("eoNumber")
			.equal(eoNumber).get();
	}

	public List<ExchangeOrder> getByRecordLocator(String recordLocator) {
		return morphia.getDatastore().createQuery(ExchangeOrder.class)
				.field("recordLocator")
				.equal(recordLocator)
				.order(Sort.descending("createDateTime"))
				.asList();
	}
}
