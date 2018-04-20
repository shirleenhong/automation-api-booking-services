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

import com.cwt.bpg.cbt.exchange.order.model.ClientMerchantFee;

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
		ClientMerchantFee merchantFee = new ClientMerchantFee();
		Mockito.when(service.putMerchantFee(merchantFee)).thenReturn(merchantFee);
		
		ResponseEntity<ClientMerchantFee> result = controller.updateMerchantFee("SG", merchantFee);

		assertNotNull(result.getBody());
		assertEquals(HttpStatus.OK, result.getStatusCode());
	}

}
