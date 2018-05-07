package com.cwt.bpg.cbt.exchange.order;

import java.util.List;

import org.mongodb.morphia.Key;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cwt.bpg.cbt.exchange.order.model.Insurance;
import com.cwt.bpg.cbt.mongodb.config.MorphiaComponent;

@Repository
public class InsuranceRepository {

	private static final Logger LOGGER = LoggerFactory.getLogger(MerchantFeeRepository.class);
	
	@Autowired
	private MorphiaComponent morphia;
	
	public List<Insurance> getAll() {
		return morphia.getDatastore().createQuery(Insurance.class)
				.asList();
	}
	
	public Insurance putInsurance(Insurance insurance) {
		Key<Insurance> save = morphia.getDatastore().save(insurance);
		LOGGER.debug("Put Result: {}", save.toString());
		
		return insurance;
	}
}
