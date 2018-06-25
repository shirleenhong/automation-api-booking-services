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

	public MerchantFee getMerchantFee(String countryCode, String profileName) {
		return morphia.getDatastore().createQuery(MerchantFee.class)
				.field("countryCode")
				.equalIgnoreCase(countryCode)
				.field("profileName")
				.equalIgnoreCase(profileName).get();
	}
	
	public MerchantFee putMerchantFee(MerchantFee fee) {

		removeMerchantFee(fee);
		
		final Datastore datastore = morphia.getDatastore();
		Key<MerchantFee> save = datastore.save(fee);
		LOGGER.info("Put Result: {}", save);
		return fee;
	}

	public MerchantFee removeMerchantFee(MerchantFee merchantFee) {
		final Datastore datastore = morphia.getDatastore();
		
		final Query<MerchantFee> clientMerchantFee = datastore.createQuery(MerchantFee.class)
                .filter("countryCode", merchantFee.getCountryCode())
                .filter("profileName", merchantFee.getProfileName());
		
		WriteResult delete = datastore.delete(clientMerchantFee);
		
		LOGGER.info("Delete Result: {}", delete);
		
		return merchantFee;
	}

}
