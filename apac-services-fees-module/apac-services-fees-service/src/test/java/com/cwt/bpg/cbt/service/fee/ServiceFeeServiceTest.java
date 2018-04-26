package com.cwt.bpg.cbt.service.fee;

import static org.junit.Assert.assertNotNull;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.cwt.bpg.cbt.service.fee.calculator.ServiceFeeCalculator;
import com.cwt.bpg.cbt.service.fee.model.PriceBreakdown;
import com.cwt.bpg.cbt.service.fee.model.PriceCalculationInput;

public class ServiceFeeServiceTest {

	@InjectMocks
	private ServiceFeeService service;
	
	@Mock
	private ServiceFeeCalculator calculator;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}
		
	@Test
	public void shouldReturnResult() throws IOException {
		
		PriceBreakdown result = new PriceBreakdown();
		PriceCalculationInput input = new PriceCalculationInput();
		Mockito.when(calculator.calculateFee(input)).thenReturn(result);
		assertNotNull(service.calculate(input));
	}
}
