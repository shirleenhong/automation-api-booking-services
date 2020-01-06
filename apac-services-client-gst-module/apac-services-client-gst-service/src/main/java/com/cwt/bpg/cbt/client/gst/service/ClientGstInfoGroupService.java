package com.cwt.bpg.cbt.client.gst.service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cwt.bpg.cbt.client.gst.model.ClientGstInfo;
import com.cwt.bpg.cbt.client.gst.repository.ClientGstInfoGroupRepository;
import com.cwt.bpg.cbt.repository.CommonRepository;
import com.cwt.bpg.cbt.service.CollectionGroupService;
import com.cwt.bpg.cbt.upload.model.CollectionGroup;
import com.cwt.bpg.cbt.upload.model.CollectionGroupContext;

@Service
public class ClientGstInfoGroupService extends CollectionGroupService<ClientGstInfo>
{
    private static final String COLLECTION_NAME = "clientGstInfo";
    
    private ClientGstInfoGroupRepository groupRespository;

    @Autowired
    public ClientGstInfoGroupService(CommonRepository<ClientGstInfo, ObjectId> repository, ClientGstInfoGroupRepository groupRespository)
    {
        super(repository, groupRespository);
        this.groupRespository = groupRespository;
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public CollectionGroupContext<ClientGstInfo> createCollectionGroup(List<ClientGstInfo> data)
    {
        CollectionGroup group = new CollectionGroup();
        group.setGroupId(UUID.randomUUID().toString());
        group.setBranchVersion(getBranchVersion());
        group.setCollectionName(COLLECTION_NAME);
        group.setActive(true);
        group.setCreationTimestamp(Instant.now());

        data.stream().forEach(clientGstInfo -> clientGstInfo.setGroupId(group.getGroupId()));

        return new CollectionGroupContext(group, data);
    }
    
    public CollectionGroup getActiveCollectionGroup()
    {
        return groupRespository.getActiveCollectionGroup(COLLECTION_NAME);
    }
}
