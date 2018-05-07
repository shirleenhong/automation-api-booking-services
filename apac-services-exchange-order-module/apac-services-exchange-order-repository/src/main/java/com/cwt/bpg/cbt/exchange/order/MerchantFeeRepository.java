package com.cwt.bpg.cbt.exchange.order;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Key;
import org.mongodb.morphia.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cwt.bpg.cbt.exchange.order.model.MerchantFee;
import com.cwt.bpg.cbt.mongodb.config.MorphiaComponent;
import com.mongodb.WriteResult;

@Repository
public class MerchantFeeRepository {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(MerchantFeeRepository.class);
	
	@Autowired
	private MorphiaComponent morphia;

	public MerchantFee getMerchantFee(String countryCode, String clienType, String profileName) {
		return morphia.getDatastore().createQuery(MerchantFee.class)
			.field("countryCode")
			.equal(countryCode)
			.field("clientType")
			.equal(clienType)
			.field("profileName")
			.equal(profileName).get();
	}
	
	public MerchantFee putMerchantFee(MerchantFee fee) {
		final Datastore datastore = morphia.getDatastore();
		
		final Query<MerchantFee> clientMerchantFee = datastore.createQuery(MerchantFee.class)
                .filter("countryCode", fee.getCountryCode())
                .filter("clientType", fee.getClientType())
                .filter("profileName", fee.getProfileName());
		
		WriteResult delete = datastore.delete(clientMerchantFee);
		
		LOGGER.info("Delete Result: {}", delete.toString());
		
		Key<MerchantFee> save = datastore.save(fee);
		
		LOGGER.info("Put Result: {}", save.toString());
		
		return fee;
	}

}
