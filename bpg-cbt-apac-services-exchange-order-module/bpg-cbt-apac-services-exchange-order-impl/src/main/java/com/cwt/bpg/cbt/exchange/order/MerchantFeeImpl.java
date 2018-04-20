package com.cwt.bpg.cbt.exchange.order;

import java.util.List;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;
import org.mongodb.morphia.query.UpdateResults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cwt.bpg.cbt.exchange.order.model.ClientMerchantFee;
import com.cwt.bpg.cbt.mongodb.config.MorphiaComponent;

@Service
public class MerchantFeeImpl implements MerchantFeeApi {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(MerchantFeeImpl.class);
	
	@Autowired
	private MorphiaComponent morphia;

	@Override
	public ClientMerchantFee getMerchantFee(String countryCode) {
		List<ClientMerchantFee> merchantFees = morphia.getDatastore().createQuery(ClientMerchantFee.class)
			.field("countryCode")
			.equal(countryCode)
			.asList();
		return merchantFees.get(0);
	}
	
	@Override
	public ClientMerchantFee putMerchantFee(ClientMerchantFee fee) {
		final Datastore datastore = morphia.getDatastore();

		final Query<ClientMerchantFee> clientMerchantFee = datastore.createQuery(ClientMerchantFee.class)
                .filter("countryCode", fee.getCountryCode());
		
		final UpdateOperations<ClientMerchantFee> removeOperations = datastore.createUpdateOperations(ClientMerchantFee.class)
                .removeAll("merchantFees", clientMerchantFee.asList().get(0).getMerchantFees());

		final UpdateResults removeResults = datastore.update(clientMerchantFee, removeOperations);
		LOGGER.info("Remove results: {}", removeResults.toString());
		
		final UpdateOperations<ClientMerchantFee> updateOperations = datastore.createUpdateOperations(ClientMerchantFee.class)
				.addToSet("merchantFees", fee.getMerchantFees());
		final UpdateResults results = datastore.update(clientMerchantFee, updateOperations);
		
		LOGGER.info("Update results: {}", results.toString());
		
		return fee;
	}

}
