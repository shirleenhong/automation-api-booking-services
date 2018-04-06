package com.cwt.bpg.cbt.service.fee;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.cwt.bpg.cbt.service.fee.model.PriceBreakdown;
import com.cwt.bpg.cbt.service.fee.model.PriceCalculationInput;

public class ServiceFeeControllerTest {
	
	private ServiceFeeController controller;
	
	@Mock
	private ServiceFeeApi serviceFee;
	
	@Before
	public void setup() {
		controller = new ServiceFeeController(serviceFee);
	}

	@Test
	public void canAcceptPriceInput() {
		PriceCalculationInput input = new PriceCalculationInput();
		ResponseEntity<PriceBreakdown> calculatePriceInput = controller.calculatePriceInput(input);
		
		assertEquals(HttpStatus.OK, calculatePriceInput.getStatusCode());
	}

}
