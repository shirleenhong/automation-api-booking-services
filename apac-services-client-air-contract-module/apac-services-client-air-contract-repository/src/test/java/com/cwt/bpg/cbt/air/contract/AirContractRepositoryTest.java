package com.cwt.bpg.cbt.air.contract;

import static org.mockito.Matchers.anyString;
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

import com.cwt.bpg.cbt.air.contract.model.AirContract;
import com.cwt.bpg.cbt.mongodb.config.MorphiaComponent;

public class AirContractRepositoryTest {
	
	@Mock
	private Datastore dataStore;

	@Mock
	private MorphiaComponent morphia;

	@InjectMocks
	private AirContractRepository repository;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
		Mockito.when(morphia.getDatastore()).thenReturn(dataStore);
	}

	@Test
	public void getShouldReturnAirContract() {
		Query query = Mockito.mock(Query.class);
		FieldEnd fieldEnd = Mockito.mock(FieldEnd.class);
		Mockito.when(dataStore.createQuery(AirContract.class)).thenReturn(query);
		Mockito.when(query.field(Mockito.anyString())).thenReturn(fieldEnd);
		Mockito.when(fieldEnd.equal(anyString())).thenReturn(query);

		repository.get("SG", "BR", "3407002");

		verify(morphia, times(1)).getDatastore();
		verify(dataStore, times(1)).createQuery(AirContract.class);
		verify(query, times(3)).field(anyString());
		verify(fieldEnd, times(3)).equal(anyString());
	}
}
