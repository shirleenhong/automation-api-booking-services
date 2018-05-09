package com.cwt.bpg.cbt.exchange.order;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.cwt.bpg.cbt.exchange.order.model.FeesBreakdown;
import com.cwt.bpg.cbt.exchange.order.model.VisaInput;

public class VisaFeeControllerTest {

	private VisaFeeController controller = new VisaFeeController();
	
	@Test
	public void canComputeVisaFees() {
		VisaInput input = new VisaInput();
		ResponseEntity<FeesBreakdown> computeVisaFee = controller.computeVisaFee(input);
		
		assertEquals(HttpStatus.OK, computeVisaFee.getStatusCode());
	}

}
