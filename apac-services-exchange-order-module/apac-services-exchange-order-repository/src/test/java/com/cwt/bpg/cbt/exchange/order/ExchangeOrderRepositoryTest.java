package com.cwt.bpg.cbt.exchange.order;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.UpdateOperations;

import com.cwt.bpg.cbt.exchange.order.model.ExchangeOrder;
import com.cwt.bpg.cbt.mongodb.config.MorphiaComponent;

public class ExchangeOrderRepositoryTest {
	
	@Mock
	private MorphiaComponent morphia;
	
	@Mock
	private UpdateOperations<ExchangeOrder> operation;
	
	@Mock
	private Datastore dataStore;
	
	@InjectMocks
	private ExchangeOrderRepository repository;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		when(morphia.getDatastore()).thenReturn(dataStore);
	}

	@Test
	public void canSaveOrUpdate() {
		ExchangeOrder eo = new ExchangeOrder();
		eo.setSequenceNumber("123098");
		
		when(dataStore.createUpdateOperations(ExchangeOrder.class)).thenReturn(operation);
		
		
		ExchangeOrder saveOrUpdate = repository.saveOrUpdate(eo);
		
		verify(dataStore, times(1)).createUpdateOperations(ExchangeOrder.class);
		verify(dataStore, times(1)).update(eo, operation);
		assertEquals("123098", saveOrUpdate.getSequenceNumber());
	}

}
