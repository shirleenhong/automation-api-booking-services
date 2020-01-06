package com.cwt.bpg.cbt.air.transaction;

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

import com.cwt.bpg.cbt.air.transaction.model.AirTransaction;
import com.cwt.bpg.cbt.upload.model.CollectionGroup;
import com.cwt.bpg.cbt.upload.model.CollectionGroupContext;

public class AirTransactionGroupServiceTest
{
    @Mock
    private AirTransactionGroupRepository groupRepository;
    
    @Mock
    private AirTransactionRepository repository;

    @InjectMocks
    private AirTransactionGroupService service;

    @Before
    public void init()
    {
        MockitoAnnotations.initMocks(this);
        service = new AirTransactionGroupService(repository, groupRepository);
    }

    @Test
    public void shouldCreateCollectionGroupContext()
    {
        CollectionGroupContext<AirTransaction> result = service.createCollectionGroup(Arrays.asList(new AirTransaction()));
        assertNotNull(result);
    }

    @Test
    public void shouldSaveAirTransctionGroup()
    {
        List<AirTransaction> airTransactions = new ArrayList<>();
        airTransactions.add(new AirTransaction());
       
        Mockito.when(groupRepository.getActiveCollectionGroup(Mockito.anyString())).thenReturn(new CollectionGroup());
        service.save(airTransactions);

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
