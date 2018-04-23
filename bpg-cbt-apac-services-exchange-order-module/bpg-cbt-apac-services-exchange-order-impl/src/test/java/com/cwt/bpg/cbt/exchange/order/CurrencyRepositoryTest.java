package com.cwt.bpg.cbt.exchange.order;

import static org.junit.Assert.assertNotNull;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.FieldEnd;
import org.mongodb.morphia.query.Query;

import com.cwt.bpg.cbt.exchange.order.model.CurrencyCodeRoundRule;
import com.cwt.bpg.cbt.mongodb.config.MorphiaComponent;

public class CurrencyRepositoryTest {
	
	@Mock
	private Datastore dataStore;
	
	@Mock
	private MorphiaComponent morphia;
	
	@InjectMocks
	private CurrencyRepository currencyApi;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		Mockito.when(morphia.getDatastore()).thenReturn(dataStore);
	}
	

	@SuppressWarnings("unchecked")
	@Test
	public void canGetCurrency() {
		final String currencyCode = "USD";
		
		Query<CurrencyCodeRoundRule> query = Mockito.mock(Query.class);
		FieldEnd fieldEnd = Mockito.mock(FieldEnd.class);
		Mockito.when(dataStore.createQuery(CurrencyCodeRoundRule.class)).thenReturn(query);
		Mockito.when(query.field(Mockito.anyString())).thenReturn(fieldEnd);
		Mockito.when(fieldEnd.equal(currencyCode)).thenReturn(query);
		Mockito.when(query.asList()).thenReturn(Arrays.asList(new CurrencyCodeRoundRule()));
		
		
		CurrencyCodeRoundRule roundingRule = currencyApi.getRoundingRule(currencyCode);
		
		Mockito.verify(dataStore, Mockito.times(1)).createQuery(CurrencyCodeRoundRule.class);
		
		assertNotNull(roundingRule);
	}

}
