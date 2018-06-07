package com.cwt.bpg.cbt.exchange.order.calculator.tf;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.math.BigDecimal;

import org.junit.Test;

import com.cwt.bpg.cbt.exchange.order.model.IndiaAirFeesBreakdown;
import com.cwt.bpg.cbt.exchange.order.model.IndiaAirFeesInput;

public class WithVatCalculatorTest {
	private WithVatCalculator withVatCalculator = new WithVatCalculator();

	@Test
	public void getTotalFeeShouldReturnTotalFee() {

		BigDecimal baseFare = new BigDecimal(1);
		BigDecimal totalTaxes = new BigDecimal(1);
		BigDecimal totalGst = new BigDecimal(1);
		BigDecimal totalAirlineCommission = new BigDecimal(1);
		BigDecimal totalReturnableOr = new BigDecimal(1);

		IndiaAirFeesInput input = new IndiaAirFeesInput();
		input.setBaseFare(baseFare);
		IndiaAirFeesBreakdown breakdown = new IndiaAirFeesBreakdown();
		breakdown.setTotalTaxes(new BigDecimal(1));
		breakdown.setTotalGst(new BigDecimal(1));
		breakdown.setTotalIataCommission(new BigDecimal(1));
		breakdown.setTotalOverheadCommission(new BigDecimal(1));

		BigDecimal expectedResult = baseFare.add(totalTaxes).add(totalGst).subtract(totalAirlineCommission)
				.subtract(totalReturnableOr);

		BigDecimal actualResult = withVatCalculator.getTotalFee(input, breakdown);

		assertNotNull(actualResult);
		assertEquals(expectedResult, actualResult);
	}
}
