package com.cwt.bpg.cbt.exchange.order;

import com.cwt.bpg.cbt.exchange.order.model.City;
import com.cwt.bpg.cbt.mongodb.config.MorphiaComponent;
import com.mongodb.WriteResult;
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

import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class CityRepositoryTest {
    @Mock
    private Datastore dataStore;

    @Mock
    private MorphiaComponent morphia;

    @InjectMocks
    private CityRepository repository;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        Mockito.when(morphia.getDatastore()).thenReturn(dataStore);
    }

    @Test
    public void getCitiesShouldReturnCities(){
        List<City> cities = repository.getCities("MNL");

        assertNotNull(cities);
        verify(morphia,times(1)).getDatastore();
        verify(dataStore,times(1)).createQuery(City.class);
    }

    @Test
    public void removeShouldReturnWriteResult(){
        Query query = Mockito.mock(Query.class);
        FieldEnd fieldEnd = Mockito.mock(FieldEnd.class);
        WriteResult result = Mockito.mock(WriteResult.class);
        City city = new City();
        city.setCode("MNL");city.setCountryCode("PH");
        Mockito.when(dataStore.createQuery(City.class)).thenReturn(query);
        Mockito.when(query.field(Mockito.anyString())).thenReturn(fieldEnd);
        Mockito.when(fieldEnd.equal(anyString())).thenReturn(query);
        Mockito.when(dataStore.delete(any(Query.class))).thenReturn(result);

        repository.remove(city);

        verify(morphia,times(2)).getDatastore();
        verify(dataStore,times(1)).delete(any(Query.class));
    }
}
