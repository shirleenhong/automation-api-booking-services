package com.cwt.bpg.cbt.exchange.order;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
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
import org.mongodb.morphia.query.FieldEnd;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;

import com.cwt.bpg.cbt.exchange.order.model.Client;
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
		eo.setEoNumber("123098");
		
		when(dataStore.createUpdateOperations(ExchangeOrder.class)).thenReturn(operation);
				
		String result = repository.saveOrUpdate(eo);
		
		verify(dataStore, times(1)).save(eo);
		assertEquals(eo.getEoNumber(), result);
	}
	
	@Test
	public void canGetExchangeOrder() {
		Query<ExchangeOrder> query = Mockito.mock(Query.class);
		FieldEnd fieldEnd = Mockito.mock(FieldEnd.class);
		Mockito.when(dataStore.createQuery(ExchangeOrder.class)).thenReturn(query);
		Mockito.when(query.field(Mockito.anyString())).thenReturn(fieldEnd);
		Mockito.when(fieldEnd.equal("eoNumber")).thenReturn(query);
		Mockito.when(query.get()).thenReturn(new ExchangeOrder());
		
		
		ExchangeOrder result = repository.getExchangeOrder("eoNumber");
		
		Mockito.verify(morphia, Mockito.times(1)).getDatastore();
		Mockito.verify(dataStore, Mockito.times(1)).createQuery(ExchangeOrder.class);
		
		assertNotNull(result);
	}

}
