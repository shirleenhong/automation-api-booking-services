package com.cwt.bpg.cbt.exchange.order;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.Sort;

import com.cwt.bpg.cbt.exchange.order.model.RoomType;
import com.cwt.bpg.cbt.mongodb.config.MorphiaComponent;

@RunWith(MockitoJUnitRunner.class)
public class RoomTypeRepositoryTest {
	
	@Mock
	private Datastore dataStore;

	@Mock
	private MorphiaComponent morphia;

	@InjectMocks
	private RoomTypeRepository repository;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
		Mockito.when(morphia.getDatastore()).thenReturn(dataStore);
	}

	@Test
	public void shouldGetAllRoomTypes() {
		Query query = Mockito.mock(Query.class);
		Mockito.when(dataStore.createQuery(RoomType.class)).thenReturn(query);
		Mockito.when(query.order(Sort.ascending(Mockito.anyString()))).thenReturn(query);
		
		repository.getAll();

		verify(morphia, times(1)).getDatastore();
		verify(dataStore, times(1)).createQuery(RoomType.class);
		
	}

}
