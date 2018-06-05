package com.cwt.bpg.cbt.exchange.order.calculator.tf;

import com.cwt.bpg.cbt.exchange.order.model.TransactionFeesBreakdown;
import com.cwt.bpg.cbt.exchange.order.model.InAirFeesInput;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class FullFareCalculatorTest {

	private FullFareCalculator fullFareCalculator = new FullFareCalculator();

	@Test
	public void getTotalFeeShouldReturnNotNull() {
		BigDecimal baseFare = new BigDecimal(1);
		BigDecimal totalTaxes = new BigDecimal(1);

		InAirFeesInput input = new InAirFeesInput();
		input.setBaseFare(baseFare);
		TransactionFeesBreakdown breakdown = new TransactionFeesBreakdown();
		breakdown.setTotalTaxes(totalTaxes);

		BigDecimal expectedResult = baseFare.add(totalTaxes);

		BigDecimal actualResult = fullFareCalculator.getTotalFee(input, breakdown);

		assertNotNull(actualResult);
		assertEquals(expectedResult, actualResult);
	}
}
