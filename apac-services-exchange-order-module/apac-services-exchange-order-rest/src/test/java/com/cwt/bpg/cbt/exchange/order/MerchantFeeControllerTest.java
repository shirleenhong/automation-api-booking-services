package com.cwt.bpg.cbt.exchange.order;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.cwt.bpg.cbt.exchange.order.model.CurrencyCodeRoundRule;
import com.cwt.bpg.cbt.exchange.order.model.MerchantFee;

public class MerchantFeeControllerTest {
	
	@Mock
	private ExchangeOrderService service;
	
	@InjectMocks
	private MerchantFeeController controller = new MerchantFeeController();

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void canPutMerchantFee() {
		MerchantFee merchantFee = new MerchantFee();
		Mockito.when(service.putMerchantFee(merchantFee)).thenReturn(merchantFee);
		
		ResponseEntity<MerchantFee> result = controller.updateMerchantFee(merchantFee);

		assertNotNull(result.getBody());
		assertEquals(HttpStatus.OK, result.getStatusCode());
	}
	
	@Test
	public void canGetMerchantFee() {
		MerchantFee merchantFee = new MerchantFee();
		Mockito.when(service.getMerchantFee(Mockito.anyString(), Mockito.anyString(), Mockito.anyString())).thenReturn(merchantFee);
		
		ResponseEntity<MerchantFee> result = controller.getMerchantFee("SG", "TF", "product");

		assertNotNull(result.getBody());
		assertEquals(HttpStatus.OK, result.getStatusCode());
	}
	
	@Test
	public void canRemoveMerchantFee() {
		MerchantFee merchantFee = new MerchantFee();
		Mockito.when(service.remove(merchantFee)).thenReturn(merchantFee);
		
		ResponseEntity<MerchantFee> result = controller.removeMerchantFee(merchantFee);

		assertNotNull(result.getBody());
		assertEquals(HttpStatus.OK, result.getStatusCode());
	}

}
