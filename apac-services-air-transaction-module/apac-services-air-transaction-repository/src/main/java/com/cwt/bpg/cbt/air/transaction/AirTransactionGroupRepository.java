package com.cwt.bpg.cbt.air.transaction;

import org.springframework.stereotype.Repository;

import com.cwt.bpg.cbt.air.transaction.model.AirTransaction;
import com.cwt.bpg.cbt.repository.CollectionGroupRepository;
import com.cwt.bpg.cbt.upload.model.CollectionGroup;

@Repository
public class AirTransactionGroupRepository extends CollectionGroupRepository
{
    public CollectionGroup getActiveAirTransactionGroup()
    {
        return getActiveCollectionGroup(AirTransaction.class.getSimpleName());
    }

    public CollectionGroup createAirTransactionGroup()
    {
        return createCollectionGroup(AirTransaction.class.getSimpleName());
    }

    public void restorePrevAirTransactionCollection(String batchId)
    {
        restorePreviousCollection(batchId);
    }
}
