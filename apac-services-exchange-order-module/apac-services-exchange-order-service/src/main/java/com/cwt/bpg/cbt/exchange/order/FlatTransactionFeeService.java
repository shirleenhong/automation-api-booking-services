package com.cwt.bpg.cbt.exchange.order;

import java.util.ArrayList;
import java.util.List;

import com.cwt.bpg.cbt.exchange.order.model.FlatTransactionFee;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FlatTransactionFeeService
{
    @Autowired
    private FlatTransactionFeeRepository flatTransactionFeeRepository;

    @Deprecated
    public FlatTransactionFee getTransactionFee(String clientAccountNumber)
    {
        return flatTransactionFeeRepository.get(clientAccountNumber);
    }

    public List<FlatTransactionFee> getTransactionFees(List<String> clientLAccountNumberList)
    {
        if (clientLAccountNumberList != null && !clientLAccountNumberList.isEmpty())
        {
            return flatTransactionFeeRepository.get(clientLAccountNumberList);
        }
        else
        {
            return flatTransactionFeeRepository.getAll();
        }
    }

    @Deprecated
    public FlatTransactionFee save(FlatTransactionFee input)
    {
        return flatTransactionFeeRepository.put(input);
    }

    public List<FlatTransactionFee> saveTransactionFees(List<FlatTransactionFee> input)
    {
        List<FlatTransactionFee> updatedFees = new ArrayList<>();
        input.forEach(transactionFee -> {
            transactionFee.setId(transactionFee.getClientAccountNumber());
            updatedFees.add(flatTransactionFeeRepository.put(transactionFee));
        });
        return updatedFees;
    }

    public String delete(String clientAccountNumber)
    {
        return flatTransactionFeeRepository.remove(clientAccountNumber);
    }
}
