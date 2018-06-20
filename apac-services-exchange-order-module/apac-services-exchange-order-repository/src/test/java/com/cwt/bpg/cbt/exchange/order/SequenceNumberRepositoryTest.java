package com.cwt.bpg.cbt.exchange.order;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.anyList;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
import org.mongodb.morphia.query.FieldEnd;
import org.mongodb.morphia.query.Query;

import com.cwt.bpg.cbt.exchange.order.model.SequenceNumber;
import com.cwt.bpg.cbt.mongodb.config.MorphiaComponent;

public class SequenceNumberRepositoryTest {

	@Mock
	private Datastore dataStore;

	@Mock
	private MorphiaComponent morphia;

	@InjectMocks
	private SequenceNumberRepository repository;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
		when(morphia.getDatastore()).thenReturn(dataStore);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Test
	public void shouldGetSequenceNumber() {

		Query query = Mockito.mock(Query.class);
		FieldEnd fieldEnd = Mockito.mock(FieldEnd.class);
		when(dataStore.createQuery(SequenceNumber.class)).thenReturn(query);
		when(query.field(Mockito.anyString())).thenReturn(fieldEnd);
		when(fieldEnd.equal(anyString())).thenReturn(query);

		SequenceNumber sn = new SequenceNumber();
		sn.setValue(2);
		when(query.get()).thenReturn(sn);

		SequenceNumber result = repository.get("HK");
		assertEquals(2, result.getValue());
		verify(morphia, times(1)).getDatastore();
		verify(dataStore, times(1)).createQuery(SequenceNumber.class);

	}

	@SuppressWarnings("unchecked")
	@Test
	public void shouldSaveSequenceNumber() {
		SequenceNumber sequenceNum = new SequenceNumber();
		Key<SequenceNumber> mockKey = mock(Key.class);
		when(dataStore.save(sequenceNum)).thenReturn(mockKey);
		Key<SequenceNumber> result = repository.save(sequenceNum);
		assertNotNull(result);
	}

	@SuppressWarnings({ "unused", "rawtypes", "unchecked" })
	@Test
	public void shouldGetAll() {
		Query query = Mockito.mock(Query.class);
		FieldEnd fieldEnd = Mockito.mock(FieldEnd.class);
		when(dataStore.createQuery(SequenceNumber.class)).thenReturn(query);
		when(query.asList()).thenReturn(Arrays.asList(new SequenceNumber()));

		List<SequenceNumber> result = repository.getAll();
		assertEquals(1, result.size());
	}

	@SuppressWarnings("unchecked")
	@Test
	public void shouldSaveListOfSequenceNumber() {
		SequenceNumber sequenceNum = new SequenceNumber();		
		when(dataStore.save(anyList())).thenReturn(mock(Iterable.class));
		Iterable<Key<SequenceNumber>> result = repository.save(Arrays.asList(sequenceNum));
		assertNotNull(result);		
	}

}
