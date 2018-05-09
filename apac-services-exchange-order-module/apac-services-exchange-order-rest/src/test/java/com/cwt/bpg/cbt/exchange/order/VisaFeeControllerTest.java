package com.cwt.bpg.cbt.exchange.order;

import static org.junit.Assert.*;

import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.cwt.bpg.cbt.exchange.order.model.FeesBreakdown;
import com.cwt.bpg.cbt.exchange.order.model.MiscFeesInput;

public class VisaFeeControllerTest {

	private VisaFeeController controller = new VisaFeeController();
	
	@Test
	public void canComputeVisaFees() {
		MiscFeesInput input = new MiscFeesInput();
		ResponseEntity<FeesBreakdown> computeVisaFee = controller.computeVisaFee(input);
		
		assertEquals(HttpStatus.OK, computeVisaFee.getStatusCode());
	}

}
