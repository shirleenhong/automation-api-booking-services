package com.cwt.bpg.cbt.client.gst.service;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.cwt.bpg.cbt.client.gst.model.ClientGstInfo;
import com.cwt.bpg.cbt.client.gst.repository.ClientGstInfoGroupRepository;
import com.cwt.bpg.cbt.client.gst.repository.ClientGstInfoRepository;
import com.cwt.bpg.cbt.upload.model.CollectionGroup;
import com.cwt.bpg.cbt.upload.model.CollectionGroupContext;

public class ClientGstInfoGroupServiceTest
{
    @Mock
    private ClientGstInfoGroupRepository groupRepository;
    
    @Mock
    private ClientGstInfoRepository repository;

    @InjectMocks
    private ClientGstInfoGroupService service;

    @Before
    public void init()
    {
        MockitoAnnotations.initMocks(this);
        service = new ClientGstInfoGroupService(repository, groupRepository);
    }

    @Test
    public void shouldCreateCollectionGroupContext()
    {
        CollectionGroupContext<ClientGstInfo> result = service.createCollectionGroup(Arrays.asList(new ClientGstInfo()));
        assertNotNull(result);
    }

    @Test
    public void shouldSaveAirTransctionGroup()
    {
        List<ClientGstInfo> clientGstInfo = new ArrayList<>();
        clientGstInfo.add(new ClientGstInfo());
       
        Mockito.when(groupRepository.getActiveCollectionGroup(Mockito.anyString())).thenReturn(new CollectionGroup());
        service.saveCollectionGroup(clientGstInfo);

        verify(groupRepository, times(2)).put(Mockito.any(CollectionGroup.class));
    }

    @Test
    public void shouldReturnActiveCollectionGroup()
    {
        Mockito.when(groupRepository.getActiveCollectionGroup(Mockito.anyString())).thenReturn(new CollectionGroup());

        CollectionGroup group = service.getActiveCollectionGroup();

        assertNotNull(group);
    }
}
