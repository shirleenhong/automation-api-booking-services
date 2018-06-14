package com.cwt.bpg.cbt.exchange.order.calculator.tf;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.math.BigDecimal;

import org.junit.Test;

import com.cwt.bpg.cbt.exchange.order.model.IndiaAirFeesBreakdown;
import com.cwt.bpg.cbt.exchange.order.model.IndiaAirFeesInput;

public class FullFareCalculatorTest {

	private FullFareCalculator fullFareCalculator = new FullFareCalculator();

	@Test
	public void getTotalFeeShouldReturnNotNull() {
		BigDecimal baseFare = new BigDecimal(1000);
		BigDecimal totalTaxes = new BigDecimal(50);

		IndiaAirFeesInput input = new IndiaAirFeesInput();
		input.setBaseFare(baseFare);
		
		IndiaAirFeesBreakdown breakdown = new IndiaAirFeesBreakdown();
		breakdown.setTotalTaxes(totalTaxes);

		BigDecimal expectedResult = baseFare.add(totalTaxes);

		BigDecimal actualResult = fullFareCalculator.getTotalFee(input, breakdown, BigDecimal.ZERO);

		assertNotNull(actualResult);
		assertEquals(expectedResult, actualResult); 
	}
}
