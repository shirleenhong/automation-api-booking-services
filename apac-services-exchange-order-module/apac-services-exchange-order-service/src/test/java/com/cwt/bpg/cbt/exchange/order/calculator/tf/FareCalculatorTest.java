package com.cwt.bpg.cbt.exchange.order.calculator.tf;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;

import com.cwt.bpg.cbt.exchange.order.model.TransactionFeesBreakdown;
import com.cwt.bpg.cbt.exchange.order.model.TransactionFeesInput;

public class FareCalculatorTest {

	private FareCalculator fareCalc = new FareCalculator();
	
	private BigDecimal baseFare;
	private BigDecimal totalIataCommission;
	private BigDecimal expectedPositiveValue;
	private BigDecimal expectedNegativeValue;
	private TransactionFeesInput input;
	private TransactionFeesBreakdown breakdown;
	
	@Before
	public void setup() {
		baseFare = new BigDecimal(5);
		totalIataCommission = new BigDecimal(4);
		expectedPositiveValue = new BigDecimal(1);
		expectedNegativeValue = new BigDecimal(-1);
		input = new TransactionFeesInput();
		breakdown = new TransactionFeesBreakdown();
	}

	@Test
	public void shouldGetTotalFeeValidParams() {
		input.setBaseFare(baseFare);
		breakdown.setTotalIataCommission(totalIataCommission);
		
		assertNotNull(fareCalc.getTotalFee(input, breakdown));

	}

	@Test
	public void shouldGetTotalFeeDifference() {
		input.setBaseFare(baseFare);
		breakdown.setTotalIataCommission(totalIataCommission);
		
		BigDecimal actualTotalFee = fareCalc.getTotalFee(input, breakdown);
		assertEquals(expectedPositiveValue, actualTotalFee);
	}
	
	@Test
	public void shouldGetTotalFeeNullValues() {
		input.setBaseFare(null);
		breakdown.setTotalIataCommission(null);
		
		assertNotNull(fareCalc.getTotalFee(input, breakdown));
	}
	
	@Test
	public void shouldGetTotalFeeNegativeDiff() {
		input.setBaseFare(totalIataCommission);
		breakdown.setTotalIataCommission(baseFare);

		
		BigDecimal actualTotalFee = fareCalc.getTotalFee(input, breakdown);
		assertEquals(expectedNegativeValue, actualTotalFee);
	}
}
