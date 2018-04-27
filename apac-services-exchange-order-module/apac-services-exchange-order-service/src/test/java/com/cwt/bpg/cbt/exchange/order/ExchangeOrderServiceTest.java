package com.cwt.bpg.cbt.exchange.order;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.cwt.bpg.cbt.exchange.order.model.MerchantFee;

public class ExchangeOrderServiceTest {

	@Mock
	private MerchantFeeRepository merchantFeeApi;
	
	@InjectMocks
	private ExchangeOrderService service;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void canGetMerchantFee() {
		String countryCode = "SG";
		String clientType = "CL";
		String profileName = "PN";
		service.getMerchantFee(countryCode, clientType, profileName);
		Mockito.verify(merchantFeeApi, Mockito.times(1)).getMerchantFee(countryCode, clientType, profileName);
	}
	
	
	@Test
	public void canPutMerchantFee() {
		MerchantFee fee = new MerchantFee();
		service.putMerchantFee(fee);
		Mockito.verify(merchantFeeApi, Mockito.times(1)).putMerchantFee(fee);
	}

}
