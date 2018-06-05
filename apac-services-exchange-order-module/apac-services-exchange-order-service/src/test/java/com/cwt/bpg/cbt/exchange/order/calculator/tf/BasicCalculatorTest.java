package com.cwt.bpg.cbt.exchange.order.calculator.tf;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.math.BigDecimal;

import org.junit.Test;

import com.cwt.bpg.cbt.exchange.order.model.InAirFeesBreakdown;
import com.cwt.bpg.cbt.exchange.order.model.InAirFeesInput;

public class BasicCalculatorTest {
	private BasicCalculator basicCalculator = new BasicCalculator();

	@Test
	public void getTotalFeeShouldReturnBaseFare() {
		InAirFeesInput input = new InAirFeesInput();
		input.setBaseFare(new BigDecimal(6));
		InAirFeesBreakdown breakdown = new InAirFeesBreakdown();

		BigDecimal expectedResult = input.getBaseFare();
		BigDecimal actualResult = basicCalculator.getTotalFee(input, breakdown);

		assertNotNull(actualResult);
		assertEquals(expectedResult, actualResult);
	}
}
