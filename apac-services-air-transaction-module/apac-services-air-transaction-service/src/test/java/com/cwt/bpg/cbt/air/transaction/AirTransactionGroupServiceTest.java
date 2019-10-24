package com.cwt.bpg.cbt.air.transaction;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.cwt.bpg.cbt.upload.model.CollectionGroup;

public class AirTransactionGroupServiceTest
{
    @Mock
    private AirTransactionGroupRepository repository;

    @InjectMocks
    private AirTransactionGroupService service;

    @Before
    public void init()
    {
        MockitoAnnotations.initMocks(this);
        service = new AirTransactionGroupService(repository);
    }

    @Test
    public void shouldCreateAirTransactionGroupCollection()
    {
        Mockito.when(repository.getActiveCollectionGroup(Mockito.anyString())).thenReturn(new CollectionGroup());
        Mockito.when(repository.put(Mockito.any(CollectionGroup.class))).thenReturn(new CollectionGroup());

        CollectionGroup result = service.createAirTransactionGroup();

        assertNotNull(result);
    }

    @Test
    public void shouldReturnActiveAirTransctionGroup()
    {
        Mockito.when(repository.getActiveCollectionGroup(Mockito.anyString())).thenReturn(new CollectionGroup());

        CollectionGroup result = service.getAirTransactionActiveCollection();

        assertNotNull(result);
    }

    @Test
    public void shouldRollbackAirTransactionGroup()
    {
        Mockito.when(repository.getAllActiveCollectionGroup()).thenReturn(Arrays.asList(new CollectionGroup()));
        Mockito.when(repository.getByGroupId(Mockito.anyString())).thenReturn(new CollectionGroup());

        service.rollbackAirTransactionGroup("1234");

        verify(repository, times(2)).put(Mockito.any(CollectionGroup.class));
    }

}
