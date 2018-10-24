package com.cwt.bpg.cbt.exchange.order;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.bson.types.ObjectId;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.cwt.bpg.cbt.exchange.order.model.MerchantFee;

public class MerchantFeeServiceTest {

	@Mock
	private MerchantFeeRepository merchantFeeApi;
	
	@InjectMocks
	private MerchantFeeService service;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void canGetMerchantFee() {
		String countryCode = "SG";
		String clientAccountNumber = "1324";
		service.getMerchantFee(countryCode, clientAccountNumber);
		verify(merchantFeeApi, times(1)).getMerchantFee(countryCode, clientAccountNumber);
	}
	
	@Test
	public void canPutMerchantFee() {
		MerchantFee fee = new MerchantFee();
		service.putMerchantFee(fee);
		Mockito.verify(merchantFeeApi, Mockito.times(1)).put(fee);
	}
	
	@Test
	public void canRemoveMerchantFee() {
		String id = "5b2870d6284b8d1ac84300ad";
        when(merchantFeeApi.remove(any(ObjectId.class))).thenReturn(id);

        String result = service.remove(id);

        assertEquals(id, result);
        verify(merchantFeeApi, times(1)).remove(any(ObjectId.class));
	}
}
