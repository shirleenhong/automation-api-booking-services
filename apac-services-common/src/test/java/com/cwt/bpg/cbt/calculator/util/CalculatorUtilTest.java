package com.cwt.bpg.cbt.calculator.util;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import org.junit.Test;

import com.cwt.bpg.cbt.calculator.CommonCalculator;

public class CalculatorUtilTest {

	CommonCalculator calculator = new CommonCalculator();
	
	@Test
	public void shouldRoundUp() {
		assertEquals(new BigDecimal(4), calculator.round(new BigDecimal(3.5),0));
		assertEquals(new BigDecimal(4), calculator.round(new BigDecimal(3.51),0));
	}
	
	@Test
	public void shouldRoundDown() {
		assertEquals(new BigDecimal(3), calculator.round(new BigDecimal(3.4),0));
		assertEquals(new BigDecimal(3), calculator.round(new BigDecimal(3.49),0));
	}
}
