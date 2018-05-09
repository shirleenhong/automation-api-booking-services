package com.cwt.bpg.cbt.exchange.order;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.cwt.bpg.cbt.exchange.order.model.AirFeesBreakdown;
import com.cwt.bpg.cbt.exchange.order.model.AirFeesInput;

public class OtherServiceFeesControllerAirFeeTest {

	
	@Mock
	private OtherServiceFeesService service;
	
	@InjectMocks
	private OtherServiceFeesController controller;
	
	
	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void canComputeAirFees() {
		AirFeesInput input = new AirFeesInput();
		
		AirFeesBreakdown value = mock(AirFeesBreakdown.class);
		when(service.calculateAirFee(input)).thenReturn(value);
		
		ResponseEntity<AirFeesBreakdown> computeAirFees = controller.computeAirFees(input);		
		verify(service, times(1)).calculateAirFee(input);
		assertEquals(HttpStatus.OK, computeAirFees.getStatusCode());
	}

}
