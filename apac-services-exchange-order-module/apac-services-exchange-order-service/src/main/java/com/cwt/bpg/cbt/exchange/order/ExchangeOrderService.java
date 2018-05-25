package com.cwt.bpg.cbt.exchange.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.cwt.bpg.cbt.exchange.order.model.MerchantFee;

/**
 * Service facade for Exchange Order module
 */
@Service
public class ExchangeOrderService {

	@Autowired
	private MerchantFeeRepository merchantFeeRepo;
	
	@Cacheable(cacheNames="merchant-fee", key="{#countryCode, #clientType, #profileName}")
	public MerchantFee getMerchantFee(String countryCode, String clientType, String profileName) {
		return merchantFeeRepo.getMerchantFee(countryCode, clientType, profileName);
	}

	@CachePut(cacheNames="merchant-fee", key="{#fee.countryCode, #fee.clientType, #fee.profileName}")
	public MerchantFee putMerchantFee(MerchantFee fee) {
		return merchantFeeRepo.putMerchantFee(fee);
	}
	
	@CacheEvict(cacheNames="merchant-fee", key="{#fee.countryCode, #fee.clientType, #fee.profileName}")
	public MerchantFee remove(MerchantFee fee) {
		return merchantFeeRepo.removeMerchantFee(fee);
	}
}
