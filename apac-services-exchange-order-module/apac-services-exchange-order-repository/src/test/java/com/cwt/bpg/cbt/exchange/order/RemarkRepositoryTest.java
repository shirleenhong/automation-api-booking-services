package com.cwt.bpg.cbt.exchange.order;

import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.FieldEnd;
import org.mongodb.morphia.query.Query;

import com.cwt.bpg.cbt.exchange.order.model.Remark;
import com.cwt.bpg.cbt.mongodb.config.MorphiaComponent;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class RemarkRepositoryTest {

    @Mock
    private Datastore dataStore;

    @Mock
    private MorphiaComponent morphia;

    @InjectMocks
    private RemarkRepository repository;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        when(morphia.getDatastore()).thenReturn(dataStore);
    }

    @Test
    public void shouldReturnRemarks(){
        Query query = Mockito.mock(Query.class);
        FieldEnd fieldEnd = Mockito.mock(FieldEnd.class);
        when(dataStore.createQuery(Remark.class)).thenReturn(query);
        when(query.order(Mockito.anyString())).thenReturn(query);
        when(query.field(Mockito.anyString())).thenReturn(fieldEnd);
        when(fieldEnd.equalIgnoreCase(anyString())).thenReturn(query);

        repository.getRemarks("SG", "HL","E");

        verify(morphia, times(1)).getDatastore();
        verify(dataStore, times(1)).createQuery(Remark.class);
    }
}
