package com.cwt.bpg.cbt.exchange.order.calculator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.math.BigDecimal;

import org.junit.Test;

import com.cwt.bpg.cbt.exchange.order.model.AirFeesBreakdown;

public class NettCostCalculatorTest {

	@Test
	public void shouldCalculateNettCost() {
		NettCostCalculator calculator = new NettCostCalculator();
		AirFeesBreakdown result = calculator.calculateFee(new BigDecimal(125), 2.5D);
		assertEquals(new BigDecimal(121.875), result.getNettCostInEO());
	}
	

	@Test
	public void shoulNotFailOnNullInput() {
		NettCostCalculator calculator = new NettCostCalculator();
		AirFeesBreakdown result = calculator.calculateFee(null, null);
		assertNotNull(result.getNettCostInEO());
	}

}
