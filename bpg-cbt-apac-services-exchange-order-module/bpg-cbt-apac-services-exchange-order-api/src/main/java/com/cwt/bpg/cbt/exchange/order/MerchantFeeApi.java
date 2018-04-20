package com.cwt.bpg.cbt.exchange.order;

import com.cwt.bpg.cbt.exchange.order.model.ClientMerchantFee;

public interface MerchantFeeApi {
	
	ClientMerchantFee getMerchantFee(String countryCode);
	
	ClientMerchantFee putMerchantFee(ClientMerchantFee fee);

}
