package com.cwt.bpg.cbt.exchange.order;

import com.cwt.bpg.cbt.exchange.order.model.FlatTransactionFee;
import com.cwt.bpg.cbt.repository.CommonRepository;
import org.springframework.stereotype.Repository;

@Repository
public class FlatTransactionFeeRepository extends CommonRepository<FlatTransactionFee, String>
{
    public static final String KEY_COLUMN = "clientAccountNumber";

    public FlatTransactionFeeRepository() {

        super(FlatTransactionFee.class, KEY_COLUMN);
    }
}
