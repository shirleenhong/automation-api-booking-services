package com.cwt.bpg.cbt.exchange.order;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

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

import com.cwt.bpg.cbt.exchange.order.model.ExchangeOrder;
import com.cwt.bpg.cbt.mongodb.config.MorphiaComponent;

public class ExchangeOrderRepositoryTest {
	
	@Mock
	private MorphiaComponent morphia;
	
	@Mock
	private UpdateOperations<ExchangeOrder> operation;
	
	@Mock
	private Datastore dataStore;
	
    @Mock
    private Query<ExchangeOrder> query;
    
    @Mock
    private Query<ExchangeOrder> queryOrder;
	
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
				
		String result = repository.save(eo);
		
		verify(dataStore, times(1)).save(eo);
		assertEquals(eo.getEoNumber(), result);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Test
	public void canGetExchangeOrder() {
		FieldEnd fieldEnd = mock(FieldEnd.class);
		when(dataStore.createQuery(ExchangeOrder.class)).thenReturn(query);
		when(query.field(Mockito.anyString())).thenReturn(fieldEnd);
		when(fieldEnd.equal("eoNumber")).thenReturn(query);
		when(query.get()).thenReturn(new ExchangeOrder());
		
		
		ExchangeOrder result = repository.getExchangeOrder("eoNumber");
		
		verify(morphia, times(1)).getDatastore();
		verify(dataStore, times(1)).createQuery(ExchangeOrder.class);
		
		assertNotNull(result);
	}
	
	@Test
	public void canUpdate() {
		ExchangeOrder eo = new ExchangeOrder();
		eo.setEoNumber("123098");
		
		when(dataStore.merge(ExchangeOrder.class)).thenReturn(null);
				
		ExchangeOrder result = repository.update(eo);
		
		verify(dataStore, times(1)).merge(eo);
		assertEquals(eo, result);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Test(expected = Exception.class)
	public void canGetExchangeOrderByRecordLocator() {
		List<ExchangeOrder> list = new ArrayList<>();
		FieldEnd fieldEnd = mock(FieldEnd.class);
		
		when(dataStore.createQuery(ExchangeOrder.class)).thenReturn(query);
		when(query.field(Mockito.anyString())).thenReturn(fieldEnd);
		when(fieldEnd.equal("recordLocator")).thenReturn(query);
	
		when(query.asList()).thenReturn(list);
	
		List<ExchangeOrder> result = repository.getByRecordLocator("recordLocator");
		
		verify(dataStore, times(1)).createQuery(ExchangeOrder.class);		
		assertNotNull(result);
	}

}
