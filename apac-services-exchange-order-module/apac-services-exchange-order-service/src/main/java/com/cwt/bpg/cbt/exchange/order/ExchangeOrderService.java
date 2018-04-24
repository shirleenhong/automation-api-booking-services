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
	private MerchantFeeRepository merchantFeeRepo;
	
	@Autowired
	private CurrencyRepository currencyRepo;

	// TODO: annotate if Cache needed
	public MerchantFee getMerchantFee(String countryCode, String clienType, String productName) {
		return merchantFeeRepo.getMerchantFee(countryCode, clienType, productName);
	}

	// TODO: annotate if Cache needed
	public MerchantFee putMerchantFee(MerchantFee fee) {
		return merchantFeeRepo.putMerchantFee(fee);
	}
	
	// TODO: annotate if Cache needed
	public CurrencyCodeRoundRule getRoundingRule(String currencyCode) {
		return currencyRepo.getRoundingRule(currencyCode);
	}
}
