package com.cwt.bpg.cbt.exchange.order;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Key;
import org.mongodb.morphia.query.Query;

import com.cwt.bpg.cbt.exchange.order.model.Insurance;
import com.cwt.bpg.cbt.mongodb.config.MorphiaComponent;

public class InsuranceRepositoryTest {

	@Mock
	private Datastore dataStore;
	
	@Mock
	private MorphiaComponent morphia;
	
	@InjectMocks
	private InsuranceRepository repo;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		when(morphia.getDatastore()).thenReturn(dataStore);
	}
	
	@SuppressWarnings({ "unchecked" })
	@Test
	public void shouldReturnInsuranceList() {
		Query<Insurance> query = mock(Query.class);
		when(dataStore.createQuery(Insurance.class)).thenReturn(query);
		when(query.asList()).thenReturn(new ArrayList<>());
		
		assertNotNull(repo.getAll());
		
		verify(morphia, times(1)).getDatastore();
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void canPutInsurance() {
		Insurance insurance = new Insurance();
		Key<Insurance> key = mock(Key.class);
		when(dataStore.save(insurance)).thenReturn(key );
		
		repo.putInsurance(insurance);
		
		verify(dataStore, times(1)).save(insurance);
	}

}
