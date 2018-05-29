package com.cwt.bpg.cbt.exchange.order.calculator.tf;

import com.cwt.bpg.cbt.exchange.order.model.TransactionFeesBreakdown;
import com.cwt.bpg.cbt.exchange.order.model.TransactionFeesInput;
import org.junit.Test;

import java.math.BigDecimal;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;

public class BaseAndYqCalculatorTest {

	private BaseAndYqCalculator baseAndYqCalculator = new BaseAndYqCalculator();

	@Test
	public void getTotalFeeShouldReturnSumOfBaseFareAndYqTax() {
		BigDecimal baseFare = new BigDecimal(1), yqTax = new BigDecimal(1);

		TransactionFeesInput input = new TransactionFeesInput();
		input.setBaseFare(baseFare);
		input.setYqTax(yqTax);

		TransactionFeesBreakdown breakdown = new TransactionFeesBreakdown();

		BigDecimal expectedResult = baseFare.add(yqTax);
		BigDecimal actualResult = baseAndYqCalculator.getTotalFee(input, breakdown);

		assertNotNull(actualResult);
		assertEquals(expectedResult, actualResult);
	}
}
