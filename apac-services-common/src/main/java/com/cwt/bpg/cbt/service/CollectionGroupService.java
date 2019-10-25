package com.cwt.bpg.cbt.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;

import com.cwt.bpg.cbt.repository.CollectionGroupRepository;
import com.cwt.bpg.cbt.repository.CommonRepository;
import com.cwt.bpg.cbt.upload.model.CollectionGroup;
import com.cwt.bpg.cbt.upload.model.CollectionGroupContext;

public abstract class CollectionGroupService<T>
{
    @Value("${com.bpg.cbt.apac.service.version}")
    private String branchVersion;

    private CommonRepository<T, ? > repository;

    private CollectionGroupRepository groupRepository;

    public abstract CollectionGroupContext<T> createCollectionGroup(List<T> data);

    public CollectionGroupService(CommonRepository<T, ? > repository, CollectionGroupRepository groupRespository)
    {
        this.repository = repository;
        this.groupRepository = groupRespository;
    }

    public void saveCollectionGroup(List<T> data)
    {
        CollectionGroupContext<T> context = createCollectionGroup(data);

        repository.putAll(context.getData());
        groupRepository.put(context.getCollectionGroup());

        CollectionGroup currentGroup = groupRepository.getActiveCollectionGroup(context.getCollectionGroup().getCollectionName());
        if (currentGroup != null)
        {
            currentGroup.setActive(false);
            groupRepository.put(currentGroup);
        }
    }

    public String getBranchVersion()
    {
        return branchVersion;
    }
}
