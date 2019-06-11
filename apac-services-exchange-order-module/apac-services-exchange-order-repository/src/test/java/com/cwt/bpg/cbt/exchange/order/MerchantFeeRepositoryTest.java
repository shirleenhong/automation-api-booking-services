package com.cwt.bpg.cbt.exchange.order;

import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.FieldEnd;
import org.mongodb.morphia.query.Query;

import com.cwt.bpg.cbt.exchange.order.model.MerchantFee;
import com.cwt.bpg.cbt.mongodb.config.MorphiaComponent;

public class MerchantFeeRepositoryTest {

	@Mock
	private Datastore dataStore;
	
	@Mock
	private MorphiaComponent morphia;

    @Mock
    private Query<MerchantFee> query;

    @InjectMocks
	private MerchantFeeRepository impl;


    @Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
        when(dataStore.createQuery(MerchantFee.class)).thenReturn(query);
        when(morphia.getDatastore()).thenReturn(dataStore);
    }
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Test
	public void canGetMerchantFee() {
		final String countryCode = "SG";
        FieldEnd fieldEnd = mock(FieldEnd.class);
		when(query.field(anyString())).thenReturn(fieldEnd);
		when(fieldEnd.equalIgnoreCase(anyString())).thenReturn(query);
		when(query.get()).thenReturn(new MerchantFee());
		
		impl.getMerchantFee(countryCode, "12345");

		verify(query, times(2)).field(anyString());
        verify(dataStore, times(1)).createQuery(MerchantFee.class);
        verify(morphia, times(1)).getDatastore();
	}
}
