package com.cwt.bpg.cbt.exchange.order.calculator.tf;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.math.BigDecimal;

import org.junit.Test;

import com.cwt.bpg.cbt.exchange.order.model.IndiaAirFeesBreakdown;
import com.cwt.bpg.cbt.exchange.order.model.IndiaAirFeesInput;

public class BasicCalculatorTest {
	private BasicCalculator basicCalculator = new BasicCalculator();

	@Test
	public void getTotalFeeShouldReturnBaseFare() {
		IndiaAirFeesInput input = new IndiaAirFeesInput();
		input.setBaseFare(new BigDecimal(6));
		IndiaAirFeesBreakdown breakdown = new IndiaAirFeesBreakdown();

		BigDecimal expectedResult = input.getBaseFare();
		BigDecimal actualResult = basicCalculator.getTotalFee(input, breakdown);

		assertNotNull(actualResult);
		assertEquals(expectedResult, actualResult);
	}
}
