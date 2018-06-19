package com.cwt.bpg.cbt.exchange.order;

import com.cwt.bpg.cbt.exchange.order.model.Airport;
import com.cwt.bpg.cbt.exchange.order.model.Remark;
import com.cwt.bpg.cbt.mongodb.config.MorphiaComponent;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.FieldEnd;
import org.mongodb.morphia.query.Query;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
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
        Mockito.when(morphia.getDatastore()).thenReturn(dataStore);
    }

    @Test
    public void getAllShouldReturnRemarks(){
        Query query = Mockito.mock(Query.class);
        FieldEnd fieldEnd = Mockito.mock(FieldEnd.class);
        Mockito.when(dataStore.createQuery(Remark.class)).thenReturn(query);
        Mockito.when(query.field(Mockito.anyString())).thenReturn(fieldEnd);
        Mockito.when(fieldEnd.equal(anyString())).thenReturn(query);

        repository.getRemarks("HL","E");

        verify(morphia, times(1)).getDatastore();
        verify(dataStore, times(1)).createQuery(Remark.class);
    }
}