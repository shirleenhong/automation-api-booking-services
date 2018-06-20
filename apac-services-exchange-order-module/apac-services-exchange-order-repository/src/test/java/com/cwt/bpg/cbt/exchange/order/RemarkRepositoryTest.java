package com.cwt.bpg.cbt.exchange.order;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.FieldEnd;
import org.mongodb.morphia.query.Query;

import com.cwt.bpg.cbt.exchange.order.model.Remark;
import com.cwt.bpg.cbt.mongodb.config.MorphiaComponent;

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
        when(morphia.getDatastore()).thenReturn(dataStore);
    }

    @Test
    public void shouldReturnRemarks(){
        Query query = Mockito.mock(Query.class);
        FieldEnd fieldEnd = Mockito.mock(FieldEnd.class);
        when(dataStore.createQuery(Remark.class)).thenReturn(query);
        when(query.field(Mockito.anyString())).thenReturn(fieldEnd);
        when(fieldEnd.equal(anyString())).thenReturn(query);

        repository.getRemarks("HL","E");

        verify(morphia, times(1)).getDatastore();
        verify(dataStore, times(1)).createQuery(Remark.class);
    }
}
