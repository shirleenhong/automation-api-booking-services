package com.cwt.bpg.cbt.exchange.order.calculator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.math.BigDecimal;

import org.junit.Test;

import com.cwt.bpg.cbt.exchange.order.model.HkSgAirFeesBreakdown;

public class NettCostCalculatorTest {

	@Test
	public void shouldCalculateNettCost() {
		NettCostCalculator calculator = new NettCostCalculator();
		HkSgAirFeesBreakdown result = (HkSgAirFeesBreakdown) calculator.calculateFee(new BigDecimal(125), 2.5D);
		assertEquals(new BigDecimal(121.875), result.getNettCost());
	}
	

	@Test
	public void shouldNotFailOnNullInput() {
		NettCostCalculator calculator = new NettCostCalculator();
		HkSgAirFeesBreakdown result = (HkSgAirFeesBreakdown) calculator.calculateFee(null, null);
		assertNotNull(result.getNettCost());
	}

}
