package com.cwt.bpg.cbt.exchange.order;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import com.cwt.bpg.cbt.exchange.order.model.MerchantFee;

@Service
public class MerchantFeeService {

    @Autowired
    private MerchantFeeRepository merchantFeeRepo;
    
    public static final String KEY = "getAll";
    
    @Cacheable(cacheNames = "merchant-fee", key="#root.methodName")
    public List<MerchantFee> getAll() {
        return merchantFeeRepo.getAll();
    }

    @Cacheable(cacheNames = "merchant-fee", key = "{#countryCode, #clientAccountNumber}")
    public MerchantFee getMerchantFee(String countryCode, String clientAccountNumber) {
        return merchantFeeRepo.getMerchantFee(countryCode, clientAccountNumber);
    }

    
    @Caching(
    	put = {@CachePut(cacheNames = "merchant-fee", key = "{#fee.countryCode, #fee.clientAccountNumber}")},
    	evict={@CacheEvict(cacheNames = "merchant-fee", key = "#root.target.KEY")
    })
    public MerchantFee putMerchantFee(MerchantFee fee) {
        return merchantFeeRepo.putMerchantFee(fee);
    }

    
    @Caching(evict={
    	@CacheEvict(cacheNames = "merchant-fee", key = "{#fee.countryCode, #fee.clientAccountNumber}"),
    	@CacheEvict(cacheNames = "merchant-fee", key = "#root.target.KEY")
    })
    public MerchantFee remove(MerchantFee fee) {
        return merchantFeeRepo.removeMerchantFee(fee);
    }

}
