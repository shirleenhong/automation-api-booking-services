package com.cwt.bpg.cbt.exchange.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cwt.bpg.cbt.exchange.order.model.ClientMerchantFee;
import com.cwt.bpg.cbt.exchange.order.model.CurrencyCodeRoundRule;

/**
 * service facade for Exchange Order module
 */
@Service
public class ExchangeOrderService {

	@Autowired
	private MerchantFeeApi merchantFeeApi;
	
	@Autowired
	private CurrencyApi currencyApi;

	// TODO: annotate if Cache needed
	public ClientMerchantFee getMerchantFee(String countryCode) {
		return merchantFeeApi.getMerchantFee(countryCode);
	}

	// TODO: annotate if Cache needed
	public ClientMerchantFee putMerchantFee(ClientMerchantFee fee) {
		return merchantFeeApi.putMerchantFee(fee);
	}
	
	// TODO: annotate if Cache needed
	public CurrencyCodeRoundRule getRoundingRule(String currencyCode) {
		return currencyApi.getRoundingRule(currencyCode);
	}
}
