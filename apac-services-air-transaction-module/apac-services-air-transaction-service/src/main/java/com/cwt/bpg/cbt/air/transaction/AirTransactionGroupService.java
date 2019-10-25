package com.cwt.bpg.cbt.air.transaction;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cwt.bpg.cbt.air.transaction.model.AirTransaction;
import com.cwt.bpg.cbt.repository.CommonRepository;
import com.cwt.bpg.cbt.service.CollectionGroupService;
import com.cwt.bpg.cbt.upload.model.CollectionGroup;
import com.cwt.bpg.cbt.upload.model.CollectionGroupContext;

@Service
public class AirTransactionGroupService extends CollectionGroupService<AirTransaction>
{
    private static final String COLLECTION_NAME = "airTransactions";

    private AirTransactionGroupRepository groupRespository;

    @Autowired
    public AirTransactionGroupService(CommonRepository<AirTransaction, ObjectId> repository, AirTransactionGroupRepository groupRespository)
    {
        super(repository, groupRespository);
        this.groupRespository = groupRespository;
    }

    @Override
    public CollectionGroupContext<AirTransaction> createCollectionGroup(List<AirTransaction> data)
    {
        CollectionGroup group = new CollectionGroup();
        group.setGroupId(UUID.randomUUID().toString());
        group.setBranchVersion(getBranchVersion());
        group.setCollectionName(COLLECTION_NAME);
        group.setActive(true);
        group.setCreationTimestamp(Instant.now());

        data.stream().forEach(a -> a.setGroupId(group.getGroupId()));

        return new CollectionGroupContext<AirTransaction>(group, data);
    }

    public void save(List<AirTransaction> airTransaction)
    {
        saveCollectionGroup(airTransaction);
    }

    public CollectionGroup getActiveCollectionGroup()
    {
        return groupRespository.getActiveCollectionGroup(COLLECTION_NAME);
    }
}
