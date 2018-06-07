package com.cwt.bpg.cbt.exchange.order.calculator.tf;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;

import java.math.BigDecimal;

import org.junit.Test;

import com.cwt.bpg.cbt.exchange.order.model.IndiaAirFeesBreakdown;
import com.cwt.bpg.cbt.exchange.order.model.IndiaAirFeesInput;

public class BaseAndYqCalculatorTest {

	private BaseAndYqCalculator baseAndYqCalculator = new BaseAndYqCalculator();

	@Test
	public void getTotalFeeShouldReturnSumOfBaseFareAndYqTax() {
		BigDecimal baseFare = new BigDecimal(1), yqTax = new BigDecimal(1);

		IndiaAirFeesInput input = new IndiaAirFeesInput();
		input.setBaseFare(baseFare);
		input.setYqTax(yqTax);

		IndiaAirFeesBreakdown breakdown = new IndiaAirFeesBreakdown();

		BigDecimal expectedResult = baseFare.add(yqTax);
		BigDecimal actualResult = baseAndYqCalculator.getTotalFee(input, breakdown);

		assertNotNull(actualResult);
		assertEquals(expectedResult, actualResult);
	}
}
