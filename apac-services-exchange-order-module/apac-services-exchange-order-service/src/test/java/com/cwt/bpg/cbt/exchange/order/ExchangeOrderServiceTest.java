package com.cwt.bpg.cbt.exchange.order;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.cwt.bpg.cbt.exchange.order.model.CurrencyCodeRoundRule;
import com.cwt.bpg.cbt.exchange.order.model.MerchantFee;

public class ExchangeOrderServiceTest {

	@Mock
	private MerchantFeeRepository merchantFeeApi;
	
	@InjectMocks
	private ExchangeOrderService service;
	
	@Mock
	private ClientRepository clientRepo;
	
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
		verify(merchantFeeApi, times(1)).getMerchantFee(countryCode, clientType, profileName);
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
	
	@Test
	public void canGetClient() {
		String proName = "profileName";
		service.getClient(proName);
		Mockito.verify(clientRepo, Mockito.times(1)).getClient(proName);
	}
	
	@Test
	public void canGetDefaultClient() {
		service.getDefaultClient();
		Mockito.verify(clientRepo, Mockito.times(1)).getClient(-1);
	}

}
