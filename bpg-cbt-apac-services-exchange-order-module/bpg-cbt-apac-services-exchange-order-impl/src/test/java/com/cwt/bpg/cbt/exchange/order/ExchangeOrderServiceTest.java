package com.cwt.bpg.cbt.exchange.order;

import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.cwt.bpg.cbt.exchange.order.model.ClientMerchantFee;

public class ExchangeOrderServiceTest {

	@Mock
	private MerchantFeeApi merchantFeeApi;
	
	@InjectMocks
	private ExchangeOrderService service;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void canGetMerchantFee() {
		String countryCode = "SG";
		service.getMerchantFee(countryCode);
		Mockito.verify(merchantFeeApi, Mockito.times(1)).getMerchantFee(countryCode);
	}
	
	
	@Test
	public void canPutMerchantFee() {
		ClientMerchantFee fee = new ClientMerchantFee();
		service.putMerchantFee(fee);
		Mockito.verify(merchantFeeApi, Mockito.times(1)).putMerchantFee(fee);
	}

}
