package com.cwt.bpg.cbt.exchange.order;

import com.cwt.bpg.cbt.exchange.order.model.FlatTransactionFee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FlatTransactionFeeService
{
    @Autowired
    private FlatTransactionFeeRepository flatTransactionFeeRepository;

    public FlatTransactionFee getTransactionFee(String clientAccountNumber)
    {
        return flatTransactionFeeRepository.get(clientAccountNumber);
    }

    public FlatTransactionFee save(FlatTransactionFee input)
    {
        return flatTransactionFeeRepository.put(input);
    }

    public String delete(String clientAccountNumber)
    {
        return flatTransactionFeeRepository.remove(clientAccountNumber);
    }
}
