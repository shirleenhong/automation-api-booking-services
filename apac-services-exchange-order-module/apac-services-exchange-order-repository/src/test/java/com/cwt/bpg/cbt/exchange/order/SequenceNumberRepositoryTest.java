package com.cwt.bpg.cbt.exchange.order;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.anyList;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Key;
import org.mongodb.morphia.query.CriteriaContainerImpl;
import org.mongodb.morphia.query.FieldEnd;
import org.mongodb.morphia.query.Query;

import com.cwt.bpg.cbt.exchange.order.model.SequenceNumber;
import com.cwt.bpg.cbt.mongodb.config.MorphiaComponent;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class SequenceNumberRepositoryTest {

	@Mock
	private Datastore dataStore;

	@Mock
	private MorphiaComponent morphia;

	@InjectMocks
	private SequenceNumberRepository repository;

	@Mock
	private Query query;
	
	@Mock
	private FieldEnd fieldEnd;
	
	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
		when(morphia.getDatastore()).thenReturn(dataStore);
	}

	@Test
	public void shouldGetSequenceNumber() {

		when(dataStore.createQuery(SequenceNumber.class)).thenReturn(query);
		when(query.field(Mockito.anyString())).thenReturn(fieldEnd);
		when(fieldEnd.equalIgnoreCase(anyString())).thenReturn(mock(CriteriaContainerImpl.class));
		when(query.criteria(anyString())).thenReturn(fieldEnd);
		
		SequenceNumber sn = new SequenceNumber();
		sn.setValue(2);
		when(query.asList()).thenReturn(Arrays.asList(sn));
		
		SequenceNumber result = repository.get("HK").get(0);
		assertEquals(2, result.getValue());
		verify(morphia, times(1)).getDatastore();
		verify(dataStore, times(1)).createQuery(SequenceNumber.class);

	}

	@Test
	public void shouldGetMultiSequenceNumber() {

		when(dataStore.createQuery(SequenceNumber.class)).thenReturn(query);
		when(query.field(Mockito.anyString())).thenReturn(fieldEnd);
		when(fieldEnd.equalIgnoreCase(anyString())).thenReturn(mock(CriteriaContainerImpl.class));
		when(query.criteria(anyString())).thenReturn(fieldEnd);
		
		SequenceNumber sn = new SequenceNumber();
		sn.setValue(2);
		when(query.asList()).thenReturn(Arrays.asList(sn, sn));
		
		List<SequenceNumber> result = repository.get("HK", "SG");
		assertEquals(2, result.get(0).getValue());
		
		verify(query, times(2)).criteria(anyString());
		verify(morphia, times(1)).getDatastore();
		verify(dataStore, times(1)).createQuery(SequenceNumber.class);

	}

	@Test
	public void shouldSaveSequenceNumber() {
		SequenceNumber sequenceNumber = new SequenceNumber();
		Key<SequenceNumber> mockKey = mock(Key.class);
		when(dataStore.save(sequenceNumber)).thenReturn(mockKey);
		Key<SequenceNumber> result = repository.save(sequenceNumber);
		assertNotNull(result);
	}

	@Test
	public void shouldGetAll() {
		when(dataStore.createQuery(SequenceNumber.class)).thenReturn(query);
		when(query.asList()).thenReturn(Arrays.asList(new SequenceNumber()));
		when(query.criteria(anyString())).thenReturn(fieldEnd);		

		List<SequenceNumber> result = repository.get("HK", "SG");
		assertEquals(1, result.size());
	}

	@Test
	public void shouldSaveListOfSequenceNumber() {
		SequenceNumber sequenceNumber = new SequenceNumber();		
		when(dataStore.save(anyList())).thenReturn(mock(Iterable.class));
		Iterable<Key<SequenceNumber>> result = repository.save(Arrays.asList(sequenceNumber));
		assertNotNull(result);		
	}

}
