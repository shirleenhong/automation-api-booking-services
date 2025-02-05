package com.cwt.bpg.cbt.exchange.order;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.cwt.bpg.cbt.exchange.order.model.MerchantFee;

public class MerchantFeeControllerTest {
	
	@Mock
	private MerchantFeeService service;
	
	@InjectMocks
	private MerchantFeeController controller = new MerchantFeeController();

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void canPutMerchantFee() {
		MerchantFee merchantFee = new MerchantFee();
		merchantFee.setCountryCode("SG");
		when(service.putMerchantFee(merchantFee)).thenReturn(merchantFee);
		
		ResponseEntity<MerchantFee> result = controller.updateMerchantFee(merchantFee);

		assertNotNull(result.getBody());
		assertEquals(HttpStatus.OK, result.getStatusCode());
	}
	
	@Test
	public void canGetMerchantFee() {
		MerchantFee merchantFee = new MerchantFee();
		when(service.getMerchantFee(Mockito.anyString(), Mockito.anyString())).thenReturn(merchantFee);
		
		ResponseEntity<MerchantFee> result = controller.getMerchantFee("SG", "product");

		assertNotNull(result.getBody());
		assertEquals(HttpStatus.OK, result.getStatusCode());
	}
	
	@Test
	public void canGetAllMerchantFees() {
		List<MerchantFee> merchantFees = new ArrayList<>();
		when(service.getAll()).thenReturn(merchantFees);

		ResponseEntity<List<MerchantFee>> result = controller.getMerchantFees();

		assertNotNull(result.getBody());
		assertEquals(HttpStatus.OK, result.getStatusCode());
	}
	
	@Test
	public void canRemoveMerchantFee() {
		String id = "5b2870d6284b8d1ac84300ad";
		when(service.remove(id)).thenReturn(id);
		
		ResponseEntity<String> result = controller.removeMerchantFee(id);

		assertNotNull(result.getBody());
		assertEquals(HttpStatus.OK, result.getStatusCode());
	}

}
