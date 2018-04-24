package com.cwt.bpg.cbt.exchange.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cwt.bpg.cbt.exchange.order.model.CurrencyCodeRoundRule;
import com.cwt.bpg.cbt.exchange.order.model.MerchantFee;

/**
 * Service facade for Exchange Order module
 */
@Service
public class ExchangeOrderService {

	@Autowired
	private MerchantFeeApi merchantFeeApi;
	
	@Autowired
	private CurrencyApi currencyApi;

	// TODO: annotate if Cache needed
	public MerchantFee getMerchantFee(String countryCode, String clienType, String productName) {
		return merchantFeeApi.getMerchantFee(countryCode, clienType, productName);
	}

	// TODO: annotate if Cache needed
	public MerchantFee putMerchantFee(MerchantFee fee) {
		return merchantFeeApi.putMerchantFee(fee);
	}
	
	// TODO: annotate if Cache needed
	public CurrencyCodeRoundRule getRoundingRule(String currencyCode) {
		return currencyApi.getRoundingRule(currencyCode);
	}
}
