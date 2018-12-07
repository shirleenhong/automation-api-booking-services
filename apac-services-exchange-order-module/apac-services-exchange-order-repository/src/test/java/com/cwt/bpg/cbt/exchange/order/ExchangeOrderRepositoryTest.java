package com.cwt.bpg.cbt.exchange.order;

import static junit.framework.TestCase.assertTrue;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Key;
import org.mongodb.morphia.query.FieldEnd;
import org.mongodb.morphia.query.FindOptions;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;
import org.mongodb.morphia.query.UpdateResults;

import com.cwt.bpg.cbt.exchange.order.model.BaseExchangeOrder;
import com.cwt.bpg.cbt.exchange.order.model.EoStatus;
import com.cwt.bpg.cbt.exchange.order.model.ExchangeOrder;
import com.cwt.bpg.cbt.exchange.order.model.ExchangeOrderSearchParam;
import com.cwt.bpg.cbt.exchange.order.model.Vendor;
import com.cwt.bpg.cbt.exchange.order.model.india.IndiaExchangeOrder;
import com.cwt.bpg.cbt.mongodb.config.MorphiaComponent;
import com.mongodb.WriteResult;

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
    private Query<IndiaExchangeOrder> indiaQuery;
    
    @Mock
    private Query<ExchangeOrder> queryOrder;
    
    @Mock
    private Key<ExchangeOrder> eoKey;

    @Mock
    private Key<IndiaExchangeOrder> indiaEoKey;

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
		
		eoKey.setCollection("exchangeOrderTransactions");
		when(eoKey.getId()).thenReturn(eo.getEoNumber());
		when(dataStore.save(eo)).thenReturn(eoKey);
				
		String result = repository.save(eo);
		
		verify(dataStore, times(1)).save(eo);
		assertThat(result, is(equalTo(eo.getEoNumber())));
	}

    @Test
    public void canSaveOrUpdateIndiaExchangeOrder() {
        IndiaExchangeOrder eo = new IndiaExchangeOrder();
        eo.setEoNumber("123098");

        indiaEoKey.setCollection("exchangeOrderTransactions");
        when(indiaEoKey.getId()).thenReturn(eo.getEoNumber());
        when(dataStore.save(eo)).thenReturn(indiaEoKey);

        String result = repository.save(eo);

        verify(dataStore, times(1)).save(eo);
        assertThat(result, is(equalTo(eo.getEoNumber())));
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

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Test
    public void canGetIndiaExchangeOrder() {
        FieldEnd fieldEnd = mock(FieldEnd.class);
        when(dataStore.createQuery(IndiaExchangeOrder.class)).thenReturn(indiaQuery);
        when(indiaQuery.field(Mockito.anyString())).thenReturn(fieldEnd);
        when(fieldEnd.equal("eoNumber")).thenReturn(indiaQuery);
        when(indiaQuery.get()).thenReturn(new IndiaExchangeOrder());


        IndiaExchangeOrder result = repository.getIndiaExchangeOrder("eoNumber");

        verify(morphia, times(1)).getDatastore();
        verify(dataStore, times(1)).createQuery(IndiaExchangeOrder.class);

        assertNotNull(result);
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

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Test(expected = Exception.class)
    public void canGetIndiaExchangeOrderByRecordLocator() {
        List<IndiaExchangeOrder> list = new ArrayList<>();
        FieldEnd fieldEnd = mock(FieldEnd.class);

        when(dataStore.createQuery(IndiaExchangeOrder.class)).thenReturn(indiaQuery);
        when(indiaQuery.field(Mockito.anyString())).thenReturn(fieldEnd);
        when(fieldEnd.equal("recordLocator")).thenReturn(indiaQuery);

        when(indiaQuery.asList()).thenReturn(list);

        List<IndiaExchangeOrder> result = repository.getIndiaExchangeOrderByRecordLocator("recordLocator");

        verify(dataStore, times(1)).createQuery(IndiaExchangeOrder.class);
        assertNotNull(result);
    }

	@SuppressWarnings("unchecked")
    @Test
	public void shouldUpdateFinanceFields() {
        when(dataStore.createQuery(ExchangeOrder.class)).thenReturn(query);
        @SuppressWarnings("rawtypes")
        FieldEnd fieldEnd = mock(FieldEnd.class);
        when(query.field(Mockito.anyString())).thenReturn(fieldEnd);
        
        when(dataStore.createUpdateOperations(ExchangeOrder.class)).thenReturn(operation);
        when(operation.set(Mockito.anyString(), Mockito.anyObject())).thenReturn(operation);
        
        final UpdateResults results = mock(UpdateResults.class);
        when(results.getUpdatedExisting()).thenReturn(true);
        
        when(dataStore.update(query, operation)).thenReturn(results);
        
        final ExchangeOrder param = new ExchangeOrder();
        boolean result = repository.updateFinance(param);
        
        verify(dataStore, times(1)).createQuery(ExchangeOrder.class);
        verify(dataStore, times(1)).update(query, operation);
        
        assertNotNull(result);
    }
	
	@Test
	public void shouldSearchExchangeNumber() {
	    final ExchangeOrderSearchParam param = new ExchangeOrderSearchParam();
	    param.setVendor(new Vendor());
	    param.setEoNumber(UUID.randomUUID().toString());
	    
	    shouldVerifySingleField(param, "eoNumber", (query, fieldEnd) -> {
	        verify(query, times(1)).field("eoNumber");
	        verify(fieldEnd, times(1)).equal(param.getEoNumber());
	    });
    }
	
	@Test
    public void shouldSearchVendorCode() {
        final ExchangeOrderSearchParam param = new ExchangeOrderSearchParam();
        param.setVendor(new Vendor());
        param.getVendor().setCode(UUID.randomUUID().toString());
        param.setEoNumber(UUID.randomUUID().toString());
        
        shouldVerifySingleField(param, "vendor.code", (query, fieldEnd) -> {
            verify(query, times(1)).field("vendor.code");
            verify(fieldEnd, times(1)).equal(param.getVendor().getCode());
        });
    }
	
	@Test
    public void shouldSearchCountryCode() {
        final ExchangeOrderSearchParam param = new ExchangeOrderSearchParam();
        param.setVendor(new Vendor());
        param.setCountryCode(UUID.randomUUID().toString());
        
        shouldVerifySingleField(param, "countryCode", (query, fieldEnd) -> {
            verify(query, times(1)).field("countryCode");
            verify(fieldEnd, times(1)).equal(param.getCountryCode());
        });
    }
	
	@Test
    public void shouldSearchRaiseType() {
        final ExchangeOrderSearchParam param = new ExchangeOrderSearchParam();
        param.setVendor(new Vendor());
        param.getVendor().setRaiseType(UUID.randomUUID().toString());
        
        shouldVerifySingleField(param, "vendor.raiseType", (query, fieldEnd) -> {
            verify(query, times(1)).field("vendor.raiseType");
            verify(fieldEnd, times(1)).equal(param.getVendor().getRaiseType());
        });
    }
	
	@Test
    public void shouldSearchStatus() {
        final ExchangeOrderSearchParam param = new ExchangeOrderSearchParam();
        param.setVendor(new Vendor());
        param.setStatus(EoStatus.NEW);
        
        shouldVerifySingleField(param, "status", (query, fieldEnd) -> {
            verify(query, times(1)).field("status");
            verify(fieldEnd, times(1)).equal(param.getStatus());
        });
    }
	
	@Test
    public void shouldSearchRecordLocator() {
        final ExchangeOrderSearchParam param = new ExchangeOrderSearchParam();
        param.setVendor(new Vendor());
        param.setRecordLocator(UUID.randomUUID().toString());
        
        shouldVerifySingleField(param, "recordLocator", (query, fieldEnd) -> {
            verify(query, times(1)).field("recordLocator");
            verify(fieldEnd, times(1)).equal(param.getRecordLocator());
        });
    }
	
	@Test
    public void shouldSearchStartCreationDate() {
        final ExchangeOrderSearchParam param = new ExchangeOrderSearchParam();
        param.setVendor(new Vendor());
        param.setStartCreationDate(Instant.now());
        
        shouldVerifySingleField(param, "createDateTime", (query, fieldEnd) -> {
            verify(query, times(1)).field("createDateTime");
            verify(fieldEnd, times(1)).greaterThanOrEq(param.getStartCreationDate());
        });
    }
	
	@Test
    public void shouldSearchEndCreationDate() {
        final ExchangeOrderSearchParam param = new ExchangeOrderSearchParam();
        param.setVendor(new Vendor());
        param.setEndCreationDate(Instant.now());
        
        shouldVerifySingleField(param, "createDateTime", (query, fieldEnd) -> {
            verify(query, times(1)).field("createDateTime");
            verify(fieldEnd, times(1)).lessThanOrEq(param.getEndCreationDate());
        });
    }

    @Test
    public void shouldGetExchangeOrder() {
        FieldEnd fieldEnd = mock(FieldEnd.class);
        when(dataStore.createQuery(ExchangeOrder.class)).thenReturn(query);
        when(query.field(Mockito.anyString())).thenReturn(fieldEnd);
        when(fieldEnd.equal("eoNumber")).thenReturn(query);
        when(query.get()).thenReturn(new ExchangeOrder());


        BaseExchangeOrder result = repository.getExchangeOrder("HK","eoNumber");

        verify(morphia, times(1)).getDatastore();
        verify(dataStore, times(1)).createQuery(ExchangeOrder.class);
        assertTrue(result instanceof ExchangeOrder);
        assertNotNull(result);
    }

    @Test
    public void shouldGetIndiaExchangeOrder() {
        FieldEnd fieldEnd = mock(FieldEnd.class);
        when(dataStore.createQuery(IndiaExchangeOrder.class)).thenReturn(indiaQuery);
        when(indiaQuery.field(Mockito.anyString())).thenReturn(fieldEnd);
        when(fieldEnd.equal("eoNumber")).thenReturn(indiaQuery);
        when(indiaQuery.get()).thenReturn(new IndiaExchangeOrder());


        BaseExchangeOrder result = repository.getExchangeOrder("IN","eoNumber");

        verify(morphia, times(1)).getDatastore();
        verify(dataStore, times(1)).createQuery(IndiaExchangeOrder.class);
        assertTrue(result instanceof IndiaExchangeOrder);
        assertNotNull(result);
    }
	
	@SuppressWarnings("unchecked")
    private void shouldVerifySingleField(final ExchangeOrderSearchParam param, final String field, final VerifySingelFieldCallback callback) {
	    when(dataStore.createQuery(ExchangeOrder.class)).thenReturn(query);
        @SuppressWarnings("rawtypes")
        FieldEnd fieldEnd = mock(FieldEnd.class);
        when(query.field(Mockito.anyString())).thenReturn(fieldEnd);
        
        when(query.asList(Mockito.any(FindOptions.class))).thenReturn(Collections.emptyList());
        
        final UpdateResults results = mock(UpdateResults.class);
        when(results.getUpdatedExisting()).thenReturn(true);
        
        when(dataStore.update(query, operation)).thenReturn(results);
        
        repository.search(param);
        callback.verify(query, fieldEnd);
	}
	
	@Test
	public void shouldDeleteExchangeOrderWithNoRecordLocator() {

		ExchangeOrder exchangeOrder = new ExchangeOrder();
		exchangeOrder.setEoNumber("123098");
		
		WriteResult writeResult = mock(WriteResult.class);
		when(dataStore.delete(exchangeOrder)).thenReturn(writeResult);

        repository.remove(exchangeOrder);

        verify(morphia, times(1)).getDatastore();
        verify(dataStore, times(1)).delete(exchangeOrder);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Test
    public void shoulGetExchangeOrderNoRecordLocator() {
		List<ExchangeOrder> list = new ArrayList<>();
		
		ExchangeOrder exchangeOrder = new ExchangeOrder();
		exchangeOrder.setEoNumber("123098");
		list.add(exchangeOrder);
		
		FieldEnd fieldEnd = mock(FieldEnd.class);
		
		when(dataStore.createQuery(ExchangeOrder.class)).thenReturn(query);
		when(query.field(Mockito.anyString())).thenReturn(fieldEnd);
		when(fieldEnd.equal("createDateTime")).thenReturn(query);
		when(fieldEnd.equal("recordLocator")).thenReturn(query);
	
		when(query.asList()).thenReturn(list);
	
		List<ExchangeOrder> result = repository.getExchangeOrderNoRecordLocator();
		
		verify(dataStore, times(1)).createQuery(ExchangeOrder.class);		
		assertEquals(result, list);
    }
}

@FunctionalInterface
interface VerifySingelFieldCallback {
    
    void verify(Query<ExchangeOrder> query, @SuppressWarnings("rawtypes") FieldEnd fieldEnd);
}
