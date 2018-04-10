package com.cwt.bpg.cbt.service.fee;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.math.BigDecimal;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;

import com.cwt.bpg.cbt.service.fee.model.PriceBreakdown;
import com.cwt.bpg.cbt.service.fee.model.PriceCalculationInput;

public class ServiceFeeImplTest {
	
	private ServiceFeeApi serviceFee = new ServiceFeeImpl();

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void shouldUseNetFare() {
		System.out.println(serviceFee);
		PriceCalculationInput input = new PriceCalculationInput();
//		Base Fare = From TST
//				Taxes = From TST
//				OB Fee = From TST
//				Markup Amount = From Power Express UI
//				Airline Commission Amount = From Power Express UI
		
		input.setBaseFare(new BigDecimal(50));
		input.setTotalTaxes(new BigDecimal(39));
		input.setObFee(new BigDecimal(9));
		input.setMarkupAmount(new BigDecimal(9));
		input.setCommissionRebateAmount(new BigDecimal(20));
		
		PriceBreakdown priceBreakdown = serviceFee.calculate(input);
		
		assertNotNull(priceBreakdown);
		assertEquals(new BigDecimal(96), priceBreakdown.getAirFareWithTaxAmount());
		
	
		
	}

}
