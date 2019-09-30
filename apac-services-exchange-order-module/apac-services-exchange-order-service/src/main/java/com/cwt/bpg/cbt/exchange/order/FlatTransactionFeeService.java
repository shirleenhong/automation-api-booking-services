package com.cwt.bpg.cbt.exchange.order;

import com.cwt.bpg.cbt.exchange.order.model.FlatTransactionFee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FlatTransactionFeeService
{
    @Autowired
    private FlatTransactionFeeRepository repository;

    public FlatTransactionFee getTransactionFee(String clientAccountNumber)
    {
        return repository.get(clientAccountNumber);
    }
}
