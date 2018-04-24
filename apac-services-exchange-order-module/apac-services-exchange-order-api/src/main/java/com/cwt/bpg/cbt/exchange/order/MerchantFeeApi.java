package com.cwt.bpg.cbt.exchange.order;

import com.cwt.bpg.cbt.exchange.order.model.MerchantFee;

public interface MerchantFeeApi {
	
	MerchantFee getMerchantFee(String countryCode, String clienType, String productName);
	
	MerchantFee putMerchantFee(MerchantFee fee);

}
