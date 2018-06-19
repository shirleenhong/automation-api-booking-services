package com.cwt.bpg.cbt.exchange.order;

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
	
	public String saveOrUpdate(ExchangeOrder eo) {		
		morphia.getDatastore().save(eo);
		LOGGER.info("Save or update: Exchange order, [{}]", eo.getEoNumber());
		return eo.getEoNumber();
	}
}
