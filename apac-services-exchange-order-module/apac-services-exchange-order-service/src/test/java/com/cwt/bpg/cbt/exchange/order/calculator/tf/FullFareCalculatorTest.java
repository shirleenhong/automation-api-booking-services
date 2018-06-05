package com.cwt.bpg.cbt.exchange.order.calculator.tf;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.math.BigDecimal;

import org.junit.Test;

import com.cwt.bpg.cbt.exchange.order.model.InAirFeesBreakdown;
import com.cwt.bpg.cbt.exchange.order.model.InAirFeesInput;

public class FullFareCalculatorTest {

	private FullFareCalculator fullFareCalculator = new FullFareCalculator();

	@Test
	public void getTotalFeeShouldReturnNotNull() {
		BigDecimal baseFare = new BigDecimal(1);
		BigDecimal totalTaxes = new BigDecimal(1);

		InAirFeesInput input = new InAirFeesInput();
		input.setBaseFare(baseFare);
		InAirFeesBreakdown breakdown = new InAirFeesBreakdown();
		breakdown.setTotalTaxes(totalTaxes);

		BigDecimal expectedResult = baseFare.add(totalTaxes);

		BigDecimal actualResult = fullFareCalculator.getTotalFee(input, breakdown);

		assertNotNull(actualResult);
		assertEquals(expectedResult, actualResult);
	}
}
