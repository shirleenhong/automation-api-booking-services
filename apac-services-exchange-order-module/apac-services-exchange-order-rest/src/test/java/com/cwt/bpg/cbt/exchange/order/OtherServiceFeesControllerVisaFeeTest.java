package com.cwt.bpg.cbt.exchange.order;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.cwt.bpg.cbt.exchange.order.model.FeesBreakdown;
import com.cwt.bpg.cbt.exchange.order.model.VisaInput;

public class OtherServiceFeesControllerVisaFeeTest {
	
	@Mock
	private OtherServiceFeesService service;
	
	@InjectMocks
	private OtherServiceFeesController controller;
	
	
	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}
	
	
	@Test
	public void canComputeVisaFees() {
		VisaInput input = new VisaInput();
		ResponseEntity<FeesBreakdown> computeVisaFee = controller.computeVisaFee(input);
		
		assertEquals(HttpStatus.OK, computeVisaFee.getStatusCode());
	}

}
