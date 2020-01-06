package com.cwt.bpg.cbt.client.gst.repository;

import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.FieldEnd;
import org.mongodb.morphia.query.Query;

import com.cwt.bpg.cbt.client.gst.model.ClientGstInfo;
import com.cwt.bpg.cbt.mongodb.config.MorphiaComponent;
public class ClientGstInfoRepositoryTest
{
    @InjectMocks
    private ClientGstInfoRepository repository;

    @Mock
    private MorphiaComponent morphia;

    @Mock
    private Datastore datastore;

    @Mock
    private Query<ClientGstInfo> query;

    @Before
    public void setUp()
    {
        MockitoAnnotations.initMocks(this);

        when(datastore.createQuery(ClientGstInfo.class)).thenReturn(query);
        when(morphia.getDatastore()).thenReturn(datastore);
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Test
    public void shouldReturnAllGstinFromActiveCollection()
    {
        FieldEnd fieldEnd = mock(FieldEnd.class);
        when(query.field(anyString())).thenReturn(fieldEnd);
        when(fieldEnd.equalIgnoreCase(anyString())).thenReturn(query);
        when(query.asList()).thenReturn(Arrays.asList(new ClientGstInfo()));

        String groupId = "1234";
        List<ClientGstInfo> result = repository.getAll(groupId);

        verify(query, times(1)).field(anyString());
        verify(morphia, times(1)).getDatastore();
        assertNotNull(result);
    }
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Test
    public void shouldReturnGstinFromActiveCollection()
    {
        FieldEnd fieldEnd = mock(FieldEnd.class);
        when(query.field(anyString())).thenReturn(fieldEnd);
        when(fieldEnd.equalIgnoreCase(anyString())).thenReturn(query);
        when(query.get()).thenReturn(new ClientGstInfo());

        String groupId = "1234";
        String gstin = "4321";
        ClientGstInfo result = repository.getByGstin(groupId, gstin);

        verify(query, times(2)).field(anyString());
        verify(morphia, times(1)).getDatastore();
        assertNotNull(result);
    }

}
