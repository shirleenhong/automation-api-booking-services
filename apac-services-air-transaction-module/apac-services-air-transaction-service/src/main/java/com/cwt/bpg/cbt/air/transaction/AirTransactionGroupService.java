package com.cwt.bpg.cbt.air.transaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cwt.bpg.cbt.service.CollectionGroupService;
import com.cwt.bpg.cbt.upload.model.CollectionGroup;

@Service
public class AirTransactionGroupService extends CollectionGroupService<AirTransactionGroupRepository>
{
    private static final String AIR_TRANSACTIONS_COLLECTION_NAME = "airTransactions";
    
    @Autowired
    public AirTransactionGroupService(AirTransactionGroupRepository repository)
    {
        super(repository);
    }

    public CollectionGroup createAirTransactionGroup()
    {
        return createCollectionGroup(AIR_TRANSACTIONS_COLLECTION_NAME);
    }

    public CollectionGroup getAirTransactionActiveCollection()
    {
        return getActiveCollectionGroup(AIR_TRANSACTIONS_COLLECTION_NAME);
    }

    public void rollbackAirTransactionGroup(String groupId)
    {
        rollbackCollectionGroup(groupId);
    }
}
