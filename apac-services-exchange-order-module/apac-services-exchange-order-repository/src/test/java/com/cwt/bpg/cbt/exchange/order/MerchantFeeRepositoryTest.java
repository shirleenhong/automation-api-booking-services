package com.cwt.bpg.cbt.exchange.order;

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

import com.cwt.bpg.cbt.exchange.order.model.ClientMerchantFee;
import com.cwt.bpg.cbt.exchange.order.model.MerchantFee;
import com.cwt.bpg.cbt.mongodb.config.MorphiaComponent;
import com.mongodb.WriteResult;

public class MerchantFeeRepositoryTest {

	@Mock
	private Datastore dataStore;
	
	@Mock
	private MorphiaComponent morphia;
	
	@InjectMocks
	private MerchantFeeRepository impl;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		Mockito.when(morphia.getDatastore()).thenReturn(dataStore);
	}
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Test
	public void canGetMerchantFee() {
		final String countryCode = "SG";
		

		Query<MerchantFee> query = Mockito.mock(Query.class);
		FieldEnd fieldEnd = Mockito.mock(FieldEnd.class);
		Mockito.when(dataStore.createQuery(MerchantFee.class)).thenReturn(query);
		Mockito.when(query.field(Mockito.anyString())).thenReturn(fieldEnd);
		Mockito.when(fieldEnd.equal(Mockito.anyString())).thenReturn(query);
		Mockito.when(query.get()).thenReturn(new MerchantFee());
		
		
		
		impl.getMerchantFee(countryCode, "TF", "ALCATEL SG");
		
		Mockito.verify(morphia, Mockito.times(1)).getDatastore();
	}
	
	
	@SuppressWarnings("unchecked")
	@Test
	public void canPutMerchantFee() {
		final String countryCode = "SG";
		ClientMerchantFee fee = new ClientMerchantFee();
		fee.setCountryCode(countryCode);
		
		Query<ClientMerchantFee> q = Mockito.mock(Query.class);
		Mockito.when(dataStore.createQuery(ClientMerchantFee.class)).thenReturn(q);
		Mockito.when(q.filter("countryCode", countryCode)).thenReturn(q);
		
		WriteResult r = new WriteResult(1, true, null); 
		Mockito.when(dataStore.delete(q)).thenReturn(r);
		
		Key<ClientMerchantFee> save = new Key<>(ClientMerchantFee.class, "testCollection", "1234567890");
		Mockito.when(dataStore.save(fee)).thenReturn(save);
		
		impl.putMerchantFee(fee);
		Mockito.verify(morphia, Mockito.times(1)).getDatastore();
	}
}
