package com.cwt.bpg.cbt.exchange.order.config;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.cwt.bpg.cbt.exchange.order.calculator.HkAirCalculator;
import com.cwt.bpg.cbt.exchange.order.calculator.MiscFeeCalculator;
import com.cwt.bpg.cbt.exchange.order.calculator.NettCostCalculator;
import com.cwt.bpg.cbt.exchange.order.calculator.SgAirCalculator;
import com.cwt.bpg.cbt.exchange.order.calculator.factory.OtherServiceCalculatorFactory;

public class ExchangeOrderConfigTest {

	ExchangeOrderConfig config;
	
	@Before
	public void setup() {
		config = new ExchangeOrderConfig();
	}
	
	@Test
	public void shouldCreateMiscFeeCaclculator() {
		assertTrue(config.miscFeeCalculator() instanceof MiscFeeCalculator);
	}
	
	@Test
	public void shouldCreateHkAirCalculator() {
		assertTrue(config.hkAirCalculator() instanceof HkAirCalculator);
	}
	
	@Test
	public void shouldCreateSgAirCalculator() {
		assertTrue(config.sgAirCalculator() instanceof SgAirCalculator);
	}
	
	@Test
	public void shouldNettCostCalculator() {
		assertTrue(config.nettCostCalculator() instanceof NettCostCalculator);
	}
	
	@Test
	public void shouldCreateOsFactory() {
		assertTrue(config.otherServiceCalculatorFactory() instanceof OtherServiceCalculatorFactory);
	}

}
