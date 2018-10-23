package com.cwt.bpg.cbt.exchange.order;

import static org.apache.commons.lang.StringUtils.leftPad;

import java.util.List;
import java.util.Optional;

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
	
	private static final String COUNTRY_CODE = "countryCode";
	private static final String CLIENT_ACCOUNT_NUMBER = "clientAccountNumber";
	
	public List<MerchantFee> getAll(String countryCode) {
		Query<MerchantFee> query = morphia.getDatastore().createQuery(MerchantFee.class)
				.field(COUNTRY_CODE)
				.equalIgnoreCase(countryCode);
		return query.asList();
	}

	public MerchantFee getMerchantFee(String countryCode, String clientAccountNumber) {
		Optional<MerchantFee> merchantFee = Optional.ofNullable(morphia.getDatastore().createQuery(MerchantFee.class)
				.field(COUNTRY_CODE)
				.equalIgnoreCase(countryCode)
				.field(CLIENT_ACCOUNT_NUMBER)
				.equalIgnoreCase(leftPadZeros(clientAccountNumber)).get());
		return merchantFee.orElse(new MerchantFee());
	}
	
	public MerchantFee putMerchantFee(MerchantFee merchantFee) {
		merchantFee = removeMerchantFee(merchantFee);

        final Datastore datastore = morphia.getDatastore();
		Key<MerchantFee> save = datastore.save(merchantFee);
		LOGGER.info("Put Result: {}", save);
		return merchantFee;
	}

	public MerchantFee removeMerchantFee(MerchantFee merchantFee) {
    	merchantFee.setClientAccountNumber(leftPadZeros(merchantFee.getClientAccountNumber()));

		final Datastore datastore = morphia.getDatastore();
		
		final Query<MerchantFee> clientMerchantFee = datastore.createQuery(MerchantFee.class)
                .filter(COUNTRY_CODE, merchantFee.getCountryCode())
                .filter(CLIENT_ACCOUNT_NUMBER, merchantFee.getClientAccountNumber());
		
		WriteResult delete = datastore.delete(clientMerchantFee);
		
		LOGGER.info("Delete Result: {}", delete);
		
		return merchantFee;
	}

    private String leftPadZeros(String value) {
        return leftPad(value, 10, '0');
    }

}
