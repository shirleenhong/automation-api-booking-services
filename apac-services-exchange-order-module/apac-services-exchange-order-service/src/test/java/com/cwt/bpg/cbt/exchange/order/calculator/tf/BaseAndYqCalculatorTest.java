package com.cwt.bpg.cbt.exchange.order.calculator.tf;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;

import java.math.BigDecimal;

import org.junit.Test;

import com.cwt.bpg.cbt.exchange.order.model.InAirFeesBreakdown;
import com.cwt.bpg.cbt.exchange.order.model.InAirFeesInput;

public class BaseAndYqCalculatorTest {

	private BaseAndYqCalculator baseAndYqCalculator = new BaseAndYqCalculator();

	@Test
	public void getTotalFeeShouldReturnSumOfBaseFareAndYqTax() {
		BigDecimal baseFare = new BigDecimal(1), yqTax = new BigDecimal(1);

		InAirFeesInput input = new InAirFeesInput();
		input.setBaseFare(baseFare);
		input.setYqTax(yqTax);

		InAirFeesBreakdown breakdown = new InAirFeesBreakdown();

		BigDecimal expectedResult = baseFare.add(yqTax);
		BigDecimal actualResult = baseAndYqCalculator.getTotalFee(input, breakdown);

		assertNotNull(actualResult);
		assertEquals(expectedResult, actualResult);
	}
}
