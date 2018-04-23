package com.cwt.bpg.cbt.exchange.order;

import java.util.List;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Key;
import org.mongodb.morphia.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cwt.bpg.cbt.exchange.order.model.ClientMerchantFee;
import com.cwt.bpg.cbt.exchange.order.model.MerchantFee;
import com.cwt.bpg.cbt.mongodb.config.MorphiaComponent;
import com.mongodb.WriteResult;

@Repository
public class MerchantFeeImpl implements MerchantFeeApi {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(MerchantFeeImpl.class);
	
	@Autowired
	private MorphiaComponent morphia;

	@Override
	public MerchantFee getMerchantFee(String countryCode, String clienType, String productName) {
		return morphia.getDatastore().createQuery(MerchantFee.class)
			.field("countryCode")
			.equal(countryCode)
			.field("clientType")
			.equal(clienType)
			.field("productName")
			.equal(productName).get();
	}
	
	@Override
	public ClientMerchantFee putMerchantFee(ClientMerchantFee fee) {
		final Datastore datastore = morphia.getDatastore();
		final String countryCode = fee.getCountryCode();
		final Query<ClientMerchantFee> clientMerchantFee = datastore.createQuery(ClientMerchantFee.class)
                .filter("countryCode", countryCode);
		WriteResult delete = datastore.delete(clientMerchantFee);
		LOGGER.info("Delete Result: {}", delete.toString());
		Key<ClientMerchantFee> save = datastore.save(fee);
		LOGGER.info("Put Result: {}", save.toString());
		return fee;
	}

}
