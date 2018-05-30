package com.cwt.bpg.cbt.exchange.order.calculator.tf;

import com.cwt.bpg.cbt.exchange.order.model.TransactionFeesBreakdown;
import com.cwt.bpg.cbt.exchange.order.model.TransactionFeesInput;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class WithVatCalculatorTest {
	private WithVatCalculator withVatCalculator = new WithVatCalculator();

	@Test
	public void getTotalFeeShouldReturnTotalFee() {

		BigDecimal baseFare = new BigDecimal(1);
		BigDecimal totalTaxes = new BigDecimal(1);
		BigDecimal totalGst = new BigDecimal(1);
		BigDecimal totalAirlineCommission = new BigDecimal(1);
		BigDecimal totalReturnableOr = new BigDecimal(1);

		TransactionFeesInput input = new TransactionFeesInput();
		input.setBaseFare(baseFare);
		TransactionFeesBreakdown breakdown = new TransactionFeesBreakdown();
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
