package com.cwt.bpg.cbt.exchange.order;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.FieldEnd;
import org.mongodb.morphia.query.Query;

import com.cwt.bpg.cbt.exchange.order.model.Airport;
import com.cwt.bpg.cbt.mongodb.config.MorphiaComponent;

public class AirportRepositoryTest {
	
	@Mock
	private Datastore dataStore;

	@Mock
	private MorphiaComponent morphia;

	@InjectMocks
	private AirportRepository repository;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
		Mockito.when(morphia.getDatastore()).thenReturn(dataStore);
	}

	@Test
	public void getAirportShouldReturnAirport() {
		Query query = Mockito.mock(Query.class);
		FieldEnd fieldEnd = Mockito.mock(FieldEnd.class);
		Mockito.when(dataStore.createQuery(Airport.class)).thenReturn(query);
		Mockito.when(query.field(Mockito.anyString())).thenReturn(fieldEnd);
		Mockito.when(fieldEnd.equal(anyString())).thenReturn(query);

		repository.get("MNL");

		verify(morphia, times(1)).getDatastore();
		verify(dataStore, times(1)).createQuery(Airport.class);
	}

}
