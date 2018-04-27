package com.cwt.bpg.cbt.exchange.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
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

	@Cacheable("merchant-fee")
	public MerchantFee getMerchantFee(String countryCode, String clienType, String profileName) {
		return merchantFeeRepo.getMerchantFee(countryCode, clienType, profileName);
	}

	@CachePut(cacheNames="mechant-fee", key="{#fee.countryCode, #fee.clientType, #fee.profileName}")
	public MerchantFee putMerchantFee(MerchantFee fee) {
		return merchantFeeRepo.putMerchantFee(fee);
	}
	
	@Cacheable("currency-rounding-rule")
	public CurrencyCodeRoundRule getRoundingRule(String currencyCode) {
		return currencyRepo.getRoundingRule(currencyCode);
	}
}
