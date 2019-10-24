package com.cwt.bpg.cbt.client.gst.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cwt.bpg.cbt.client.gst.repository.ClientGstInfoGroupRepository;
import com.cwt.bpg.cbt.service.CollectionGroupService;
import com.cwt.bpg.cbt.upload.model.CollectionGroup;

@Service
public class ClientGstInfoGroupService extends CollectionGroupService<ClientGstInfoGroupRepository>
{
    private static final String GSTIN_COLLECTION_NAME = "clientGstInfo";
    
    @Autowired
    public ClientGstInfoGroupService(ClientGstInfoGroupRepository repository)
    {
        super(repository);
    }

    public CollectionGroup createClientGstInfoGroup()
    {
        return createCollectionGroup(GSTIN_COLLECTION_NAME);
    }

    public CollectionGroup getClientGstInfoActiveCollection()
    {
        return getActiveCollectionGroup(GSTIN_COLLECTION_NAME);
    }

    public void rollbackClientGstInfoGroup(String groupId)
    {
        rollbackCollectionGroup(groupId);
    }
}
