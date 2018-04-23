package com.cwt.bpg.cbt.exchange.order;

import org.junit.Before;
import org.junit.Ignore;
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
import com.cwt.bpg.cbt.mongodb.config.MorphiaComponent;
import com.mongodb.WriteResult;

// TODO: merchant fee impl is in changing state
@Ignore
public class MerchantFeeImplTest {

	@Mock
	private Datastore dataStore;
	
	@Mock
	private MorphiaComponent morphia;
	
	@InjectMocks
	private MerchantFeeImpl impl;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		Mockito.when(morphia.getDatastore()).thenReturn(dataStore);
	}
	
	
	@Test
	public void canGetMerchantFee() {
		final String countryCode = "SG";
		
		Query<ClientMerchantFee> q = Mockito.mock(Query.class);
		FieldEnd<ClientMerchantFee> f = Mockito.mock(FieldEnd.class);
		Mockito.when(dataStore.createQuery(ClientMerchantFee.class)).thenReturn(q);
		Mockito.when(q.filter("countryCode", "SG")).thenReturn(q);
		//Mockito.when(q.equal("SG")).thenReturn(f);
		
		impl.getMerchantFee(countryCode);
		
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
		
		WriteResult r = new WriteResult(1, true, null); //Mockito.mock(WriteResult.class);;
		Mockito.when(dataStore.delete(q)).thenReturn(r);
		
		Key<ClientMerchantFee> save = new Key<>(ClientMerchantFee.class, "testCollection", "1234567890");
		Mockito.when(dataStore.save(fee)).thenReturn(save);
		
		impl.putMerchantFee(fee);
		Mockito.verify(morphia, Mockito.times(1)).getDatastore();
	}


}
