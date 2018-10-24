package com.cwt.bpg.cbt.exchange.order;

import static org.apache.commons.lang.StringUtils.leftPad;

import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.stereotype.Repository;

import com.cwt.bpg.cbt.exchange.order.model.MerchantFee;
import com.cwt.bpg.cbt.repository.CommonRepository;

@Repository
public class MerchantFeeRepository extends CommonRepository<MerchantFee, ObjectId>{
		
	private static final String ID = "id";
	private static final String COUNTRY_CODE = "countryCode";
	private static final String CLIENT_ACCOUNT_NUMBER = "clientAccountNumber";
	
	public MerchantFeeRepository() {
		super(MerchantFee.class, ID);
	}

	public MerchantFee getMerchantFee(String countryCode, String clientAccountNumber) {
		Optional<MerchantFee> merchantFee = Optional.ofNullable(morphia.getDatastore().createQuery(MerchantFee.class)
				.field(COUNTRY_CODE)
				.equalIgnoreCase(countryCode)
				.field(CLIENT_ACCOUNT_NUMBER)
				.equalIgnoreCase(leftPadZeros(clientAccountNumber)).get());
		return merchantFee.orElse(new MerchantFee());
	}

    private String leftPadZeros(String value) {
        return leftPad(value, 10, '0');
    }

}
