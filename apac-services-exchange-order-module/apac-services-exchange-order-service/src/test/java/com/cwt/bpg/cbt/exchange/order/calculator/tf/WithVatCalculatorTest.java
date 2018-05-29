package com.cwt.bpg.cbt.exchange.order.calculator.tf;

import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class WithVatCalculatorTest {
	private WithVatCalculator withVatCalculator = new WithVatCalculator();

	@Test
	public void getTotalFeeShouldReturn() {

		BigDecimal baseFare = new BigDecimal(1), totalTaxes = new BigDecimal(1), totalGst = new BigDecimal(1),
				totalAirlineCommission = new BigDecimal(1), totalReturnableOr = new BigDecimal(1);

		BigDecimal expectedResult = baseFare.add(totalTaxes).add(totalGst).subtract(totalAirlineCommission)
				.subtract(totalReturnableOr);

		BigDecimal actualResult = withVatCalculator
				.getTotalFee(baseFare, totalTaxes, totalGst, totalAirlineCommission, totalReturnableOr);

		assertNotNull(actualResult);
		assertEquals(expectedResult, actualResult);
	}
}
