package com.cwt.bpg.cbt.exchange.order;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.cwt.bpg.cbt.exchange.order.model.MerchantFee;

@Service
public class MerchantFeeService
{

    @Autowired
    private MerchantFeeRepository merchantFeeRepo;

    @Cacheable(cacheNames = "merchant-fees", key = "#root.methodName")
    public List<MerchantFee> getAll()
    {
        return merchantFeeRepo.getAll();
    }

    @Cacheable(cacheNames = "merchant-fees", key = "{#countryCode, #clientAccountNumber}")
    public MerchantFee getMerchantFee(String countryCode, String clientAccountNumber)
    {
        return merchantFeeRepo.getMerchantFee(countryCode, clientAccountNumber);
    }

    @CacheEvict(cacheNames = "merchant-fees", allEntries = true)
    public MerchantFee putMerchantFee(MerchantFee fee)
    {
        return merchantFeeRepo.put(fee);
    }

    @CacheEvict(cacheNames = "merchant-fees", allEntries = true)
    public String remove(String id)
    {
        return merchantFeeRepo.remove(new ObjectId(id));
    }
}
