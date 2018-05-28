package com.cwt.bpg.cbt.exchange.order.calculator.tf;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;

public class NettFareCalculatorTest {

	private NettFareCalculator netFareCalc = new NettFareCalculator();
	
	private BigDecimal mockBigDecimalValFive;
	private BigDecimal mockBigDecimalValFour;
	private BigDecimal mockBigDecimalValOne;
	private BigDecimal mockBigDecimalValZero;
	private BigDecimal mockBigDecimalValNegative;
	
	@Before
	public void setup() {
		mockBigDecimalValFive = new BigDecimal(5);
		mockBigDecimalValFour = new BigDecimal(4);
		mockBigDecimalValOne = new BigDecimal(1);
		mockBigDecimalValZero = new BigDecimal(0);
		mockBigDecimalValNegative = new BigDecimal(-1);
	}

	@Test
	public void getTotalFee_validParams() {
		assertNotNull(netFareCalc.getTotalFee(mockBigDecimalValFive, mockBigDecimalValZero, mockBigDecimalValZero));

	}

	@Test
	public void getTotalFee_nullParams() {
		assertEquals(mockBigDecimalValZero, netFareCalc.getTotalFee(null, null, null));
	}

	@Test
	public void getTotalFee_testValue() {
		BigDecimal actualTotalFee = netFareCalc.getTotalFee(mockBigDecimalValFive, mockBigDecimalValFour,
				mockBigDecimalValZero);
		assertEquals(mockBigDecimalValOne, actualTotalFee);
	}
	
	@Test
	public void getTotalFee_testNegativeValue() {
		BigDecimal actualTotalFee = netFareCalc.getTotalFee(mockBigDecimalValFour, mockBigDecimalValFive,
				mockBigDecimalValZero);
		assertEquals(mockBigDecimalValNegative, actualTotalFee);
	}
}
