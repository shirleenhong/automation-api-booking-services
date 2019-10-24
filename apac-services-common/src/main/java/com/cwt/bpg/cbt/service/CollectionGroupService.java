package com.cwt.bpg.cbt.service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.CollectionUtils;

import com.cwt.bpg.cbt.repository.CollectionGroupRepository;
import com.cwt.bpg.cbt.upload.model.CollectionGroup;

public class CollectionGroupService<T extends CollectionGroupRepository>
{
    private T repository;
    
    @Value("${com.bpg.cbt.apac.service.version}")
    private String branchVersion;    

    public CollectionGroupService(T repository)
    {
        this.repository = repository;
    }

    protected CollectionGroup createCollectionGroup(String collectionName)
    {
        CollectionGroup activeGroup = repository.getActiveCollectionGroup(collectionName);
        if (activeGroup != null)
        {
            activeGroup.setActive(false);
            repository.put(activeGroup);
        }

        CollectionGroup newGroup = new CollectionGroup();
        newGroup.setGroupId(UUID.randomUUID().toString());
        newGroup.setCollectionName(collectionName);
        newGroup.setCreationTimestamp(Instant.now());
        newGroup.setActive(true);
        newGroup.setBranchVersion(branchVersion);

        return repository.put(newGroup);
    }

    protected void rollbackCollectionGroup(String rollbackGroupId)
    {
        List<CollectionGroup> activeCollections = repository.getAllActiveCollectionGroup();

        if (!CollectionUtils.isEmpty(activeCollections))
        {
            activeCollections.stream().forEach(c -> {
                c.setActive(false);
                repository.put(c);
            });
        }

        CollectionGroup prevCollection = repository.getByGroupId(rollbackGroupId);

        if (prevCollection != null)
        {
            prevCollection.setActive(true);
            repository.put(prevCollection);
        }
    }

    protected CollectionGroup getActiveCollectionGroup(String collectionName)
    {
        return repository.getActiveCollectionGroup(collectionName);
    }
}
