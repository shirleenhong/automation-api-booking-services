package com.cwt.bpg.cbt.exchange.order;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.Sort;

import com.cwt.bpg.cbt.exchange.order.model.VmpdReasonCode;
import com.cwt.bpg.cbt.mongodb.config.MorphiaComponent;

public class VmpdReasonCodesRepositoryTest {

    @Mock
    private Datastore dataStore;

    @Mock
    private MorphiaComponent morphia;

    @InjectMocks
    private VmpdReasonCodesRepository repository;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        when(morphia.getDatastore()).thenReturn(dataStore);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Test
    public void shouldReturnVmpdReasonCodes() {
        Query query = Mockito.mock(Query.class);
        when(dataStore.createQuery(VmpdReasonCode.class)).thenReturn(query);
        when(query.order(any(Sort.class))).thenReturn(query);

        repository.getAll();

        verify(morphia, times(1)).getDatastore();
        verify(dataStore, times(1)).createQuery(VmpdReasonCode.class);
    }
}
