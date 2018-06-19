package com.cwt.bpg.cbt.exchange.order;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

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
		String profileName = "PN";
		service.getMerchantFee(countryCode, profileName);
		verify(merchantFeeApi, times(1)).getMerchantFee(countryCode, profileName);
	}
	
	@Test
	public void canPutMerchantFee() {
		MerchantFee fee = new MerchantFee();
		service.putMerchantFee(fee);
		Mockito.verify(merchantFeeApi, Mockito.times(1)).putMerchantFee(fee);
	}
	
	@Test
	public void canRemoveMerchantFee() {
		MerchantFee fee = new MerchantFee();
		service.remove(fee);
		Mockito.verify(merchantFeeApi, Mockito.times(1)).removeMerchantFee(fee);
	}
}
