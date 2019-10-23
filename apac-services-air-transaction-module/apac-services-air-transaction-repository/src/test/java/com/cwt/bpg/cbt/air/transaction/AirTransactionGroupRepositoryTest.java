package com.cwt.bpg.cbt.air.transaction;

import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.FieldEnd;
import org.mongodb.morphia.query.FindOptions;
import org.mongodb.morphia.query.Query;

import com.cwt.bpg.cbt.mongodb.config.MorphiaComponent;
import com.cwt.bpg.cbt.upload.model.CollectionGroup;

public class AirTransactionGroupRepositoryTest
{
    @Mock
    private Datastore dataStore;

    @Mock
    private MorphiaComponent morphia;

    @InjectMocks
    private AirTransactionGroupRepository repository;

    @Before
    public void init()
    {
        MockitoAnnotations.initMocks(this);
        when(morphia.getDatastore()).thenReturn(dataStore);
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Test
    public void shouldCreateAirTransactionCollection()
    {
        Query query = Mockito.mock(Query.class);
        FieldEnd fieldEnd = Mockito.mock(FieldEnd.class);
        when(dataStore.createQuery(CollectionGroup.class)).thenReturn(query);

        when(query.field(Mockito.anyString())).thenReturn(fieldEnd);
        when(fieldEnd.equal(anyString())).thenReturn(query);

        CollectionGroup collectionGroup = repository.createAirTransactionGroup();

        verify(morphia, times(2)).getDatastore();
        verify(dataStore, times(1)).createQuery(CollectionGroup.class);
        assertNotNull(collectionGroup);
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Test
    public void shouldReturnAirTransactionActiveCollection()
    {
        Query query = Mockito.mock(Query.class);
        FieldEnd fieldEnd = Mockito.mock(FieldEnd.class);
        when(dataStore.createQuery(CollectionGroup.class)).thenReturn(query);

        when(query.field(Mockito.anyString())).thenReturn(fieldEnd);
        when(fieldEnd.equal(anyString())).thenReturn(query);
        when(query.asList(Mockito.any(FindOptions.class))).thenReturn(Arrays.asList(new CollectionGroup()));

        CollectionGroup collectionGroup = repository.getActiveAirTransactionGroup();

        verify(morphia, times(1)).getDatastore();
        verify(dataStore, times(1)).createQuery(CollectionGroup.class);
        assertNotNull(collectionGroup);
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Test
    public void shouldrestorePreviousCollection()
    {
        Query query = Mockito.mock(Query.class);
        FieldEnd fieldEnd = Mockito.mock(FieldEnd.class);
        when(dataStore.createQuery(CollectionGroup.class)).thenReturn(query);

        when(query.field(Mockito.anyString())).thenReturn(fieldEnd);
        when(fieldEnd.equal(anyString())).thenReturn(query);

        String batchId = "1234";
        repository.restorePrevAirTransactionCollection(batchId);

        verify(morphia, times(2)).getDatastore();
        verify(dataStore, times(2)).createQuery(CollectionGroup.class);

    }

}
