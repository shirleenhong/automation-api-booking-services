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
	
	private BigDecimal valFive;
	private BigDecimal valFour;
	private BigDecimal valOne;
	private BigDecimal valNegative;
	private TransactionFeesInput input;
	private TransactionFeesBreakdown breakdown;
	
	@Before
	public void setup() {
		valFive = new BigDecimal(5);
		valFour = new BigDecimal(4);
		valOne = new BigDecimal(1);
		valNegative = new BigDecimal(-1);
		input = new TransactionFeesInput();
		breakdown = new TransactionFeesBreakdown();
	}

	@Test
	public void shouldGetTotalFeeValidParams() {
		input.setBaseFare(valFive);
		breakdown.setTotalIataCommission(valFour);
		
		assertNotNull(fareCalc.getTotalFee(input, breakdown));

	}

	@Test
	public void shouldGetTotalFeeDifference() {
		input.setBaseFare(valFive);
		breakdown.setTotalIataCommission(valFour);
		
		BigDecimal actualTotalFee = fareCalc.getTotalFee(input, breakdown);
		assertEquals(valOne, actualTotalFee);
	}
	
	@Test
	public void shouldGetTotalFeeNullValues() {
		input.setBaseFare(null);
		breakdown.setTotalIataCommission(null);
		
		assertNotNull(fareCalc.getTotalFee(input, breakdown));
	}
	
	@Test
	public void shouldGetTotalFeeNegativeDiff() {
		input.setBaseFare(valFour);
		breakdown.setTotalIataCommission(valFive);

		
		BigDecimal actualTotalFee = fareCalc.getTotalFee(input, breakdown);
		assertEquals(valNegative, actualTotalFee);
	}
}
