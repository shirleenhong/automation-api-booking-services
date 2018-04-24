package com.cwt.bpg.cbt.service.fee;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.cwt.bpg.cbt.service.fee.model.PriceBreakdown;
import com.cwt.bpg.cbt.service.fee.model.PriceCalculationInput;

public class ServiceFeeControllerTest {

	@Mock
	private ServiceFeeService service;

	@InjectMocks
	private ServiceFeeController controller;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void canAcceptPriceInput() {
		PriceCalculationInput input = new PriceCalculationInput();
		ResponseEntity<PriceBreakdown> calculatePriceInput = controller.calculatePriceInput(input);

		Mockito.verify(service, Mockito.times(1)).calculate(input);
		
		assertEquals(HttpStatus.OK, calculatePriceInput.getStatusCode());
	}

}
