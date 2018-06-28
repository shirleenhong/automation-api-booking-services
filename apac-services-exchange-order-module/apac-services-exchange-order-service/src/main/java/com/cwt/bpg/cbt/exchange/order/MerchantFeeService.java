package com.cwt.bpg.cbt.exchange.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.cwt.bpg.cbt.exchange.order.model.MerchantFee;

@Service
public class MerchantFeeService {

	@Autowired
	private MerchantFeeRepository merchantFeeRepo;
	
	@Cacheable(cacheNames="merchant-fee", key="{#countryCode, #clientNumber}")
	public MerchantFee getMerchantFee(String countryCode, String clientNumber) {
		return merchantFeeRepo.getMerchantFee(countryCode, clientNumber);
	}

	@CachePut(cacheNames="merchant-fee", key="{#fee.countryCode, #fee.clientNumber}")
	public MerchantFee putMerchantFee(MerchantFee fee) {
		return merchantFeeRepo.putMerchantFee(fee);
	}
	
	@CacheEvict(cacheNames="merchant-fee", key="{#fee.countryCode, #fee.clientNumber}")
	public MerchantFee remove(MerchantFee fee) {
		return merchantFeeRepo.removeMerchantFee(fee);
	}
}
