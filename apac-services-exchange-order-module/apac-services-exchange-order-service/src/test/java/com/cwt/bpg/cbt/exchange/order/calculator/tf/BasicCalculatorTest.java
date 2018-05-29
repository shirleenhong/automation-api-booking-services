package com.cwt.bpg.cbt.exchange.order.calculator.tf;

import com.cwt.bpg.cbt.exchange.order.model.TransactionFeesBreakdown;
import com.cwt.bpg.cbt.exchange.order.model.TransactionFeesInput;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class BasicCalculatorTest {
	private BasicCalculator basicCalculator = new BasicCalculator();

	@Test
	public void getTotalFeeShouldReturnBaseFare() {
		TransactionFeesInput input = new TransactionFeesInput();
		input.setBaseFare(new BigDecimal(6));
		TransactionFeesBreakdown breakdown = new TransactionFeesBreakdown();

		BigDecimal expectedResult = input.getBaseFare();
		BigDecimal actualResult = basicCalculator.getTotalFee(input, breakdown);

		assertNotNull(actualResult);
		assertEquals(expectedResult, actualResult);
	}
}
