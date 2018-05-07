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
import org.mongodb.morphia.query.FieldEnd;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;
import org.mongodb.morphia.query.UpdateResults;

import com.cwt.bpg.cbt.exchange.order.model.Insurance;
import com.cwt.bpg.cbt.mongodb.config.MorphiaComponent;
import com.mongodb.WriteResult;

public class InsuranceRepositoryTest {

	@Mock
	private Datastore datastore;
	
	@Mock
	private MorphiaComponent morphia;
	
	@InjectMocks
	private InsuranceRepository repo;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		when(morphia.getDatastore()).thenReturn(datastore);
	}
	
	@SuppressWarnings({ "unchecked" })
	@Test
	public void shouldReturnInsuranceList() {
		Query<Insurance> query = mock(Query.class);
		when(datastore.createQuery(Insurance.class)).thenReturn(query);
		when(query.asList()).thenReturn(new ArrayList<>());
		
		assertNotNull(repo.getAll());
		
		verify(morphia, times(1)).getDatastore();
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Test
	public void canInsertOrUpdateInsurance() {
		Insurance insurance = new Insurance();
		insurance.setType("insurance-type");
		
		Key<Insurance> key = mock(Key.class);
		when(datastore.save(insurance)).thenReturn(key);
		
		repo.putInsurance(insurance);
		
		verify(datastore, times(1)).save(insurance);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Test
	public void canDeleteInsurance() {
		final String toDelete = "test";
		
		Query<Insurance> query = mock(Query.class);
		when(datastore.createQuery(Insurance.class)).thenReturn(query);
		FieldEnd fieldEnd = mock(FieldEnd.class);
		when(query.field("type")).thenReturn(fieldEnd);
		when(fieldEnd.equal(toDelete)).thenReturn(query);
		
		WriteResult key = new WriteResult(1, true, null);
		when(datastore.delete(query)).thenReturn(key);
		
		repo.remove(toDelete);
		
		verify(datastore, times(1)).delete(query);
	}
}
