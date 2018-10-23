package com.cwt.bpg.cbt.exchange.order;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.Sort;

import com.cwt.bpg.cbt.exchange.order.model.CarVendor;
import com.cwt.bpg.cbt.mongodb.config.MorphiaComponent;

public class CarVendorRepositoryTest {

    @Mock
    private Datastore dataStore;

    @Mock
    private MorphiaComponent morphia;

    @InjectMocks
    private CarVendorRepository repository;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        when(morphia.getDatastore()).thenReturn(dataStore);
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
	@Test
    public void shouldReturnVmpdReasonCodes(){
    	Query query = Mockito.mock(Query.class);
        when(dataStore.createQuery(CarVendor.class)).thenReturn(query);
        when(query.order(Sort.ascending(Mockito.anyString()))).thenReturn(query);

        repository.getAll();

        verify(morphia, times(1)).getDatastore();
        verify(dataStore, times(1)).createQuery(CarVendor.class);
    }
}