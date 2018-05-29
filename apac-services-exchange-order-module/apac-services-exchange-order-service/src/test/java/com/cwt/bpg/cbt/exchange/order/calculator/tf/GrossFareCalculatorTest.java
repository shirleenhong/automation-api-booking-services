package com.cwt.bpg.cbt.exchange.order.calculator.tf;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;

import com.cwt.bpg.cbt.exchange.order.model.TransactionFeesBreakdown;
import com.cwt.bpg.cbt.exchange.order.model.TransactionFeesInput;

public class GrossFareCalculatorTest {

	private GrossFareCalculator netFareCalc = new GrossFareCalculator();
	
	private BigDecimal mockBigDecimalValFive;
	private BigDecimal mockBigDecimalValFour;
	private BigDecimal mockBigDecimalValOne;
	private BigDecimal mockBigDecimalValTen;
	private TransactionFeesInput input;
	private TransactionFeesBreakdown breakdown;
	
	@Before
	public void setup() {
		mockBigDecimalValFive = new BigDecimal(5);
		mockBigDecimalValFour = new BigDecimal(4);
		mockBigDecimalValOne = new BigDecimal(1);
		mockBigDecimalValTen = new BigDecimal(10);
		input = new TransactionFeesInput();
		breakdown = new TransactionFeesBreakdown();
	}

	@Test
	public void shouldGetTotalFeeValidParams() {
		input.setBaseFare(mockBigDecimalValFive);
		breakdown.setTotalTaxes(mockBigDecimalValFour);
		breakdown.setTotalGst(mockBigDecimalValOne);
		
		assertNotNull(netFareCalc.getTotalFee(input, breakdown));

	}

	@Test
	public void getTotalFee_nullParams() {
		assertNull(netFareCalc.getTotalFee(null, breakdown));
		assertNull(netFareCalc.getTotalFee(input, null));
		assertNull(netFareCalc.getTotalFee(null, null));
	}

	@Test
	public void getTotalFee_testValue() {
		input.setBaseFare(mockBigDecimalValFive);
		breakdown.setTotalTaxes(mockBigDecimalValFour);
		breakdown.setTotalGst(mockBigDecimalValOne);
		
		BigDecimal actualTotalFee = netFareCalc.getTotalFee(input, breakdown);
		assertEquals(mockBigDecimalValTen, actualTotalFee);
	}
	
}
