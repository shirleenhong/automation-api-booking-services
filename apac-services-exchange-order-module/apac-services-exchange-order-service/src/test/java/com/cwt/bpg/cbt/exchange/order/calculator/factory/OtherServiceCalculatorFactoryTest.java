package com.cwt.bpg.cbt.exchange.order.calculator.factory;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

import com.cwt.bpg.cbt.calculator.model.Country;
import com.cwt.bpg.cbt.exchange.order.calculator.HkAirCalculator;
import com.cwt.bpg.cbt.exchange.order.calculator.SgAirCalculator;

public class OtherServiceCalculatorFactoryTest {

	private OtherServiceCalculatorFactory factory = new OtherServiceCalculatorFactory();
	
	@Before
	public void setup() {
		ReflectionTestUtils.setField(factory, "hkAirCalculator", new HkAirCalculator());
		ReflectionTestUtils.setField(factory, "sgAirCalculator", new SgAirCalculator());
		factory.init();
	}

	@Test
	public void shouldGetHkCalculator() {
		assertTrue(factory.getCalculator(Country.HONG_KONG.getCode()) instanceof HkAirCalculator);
	}
	
	@Test
	public void shouldGetSgCalculator() {
		assertTrue(factory.getCalculator(Country.SINGAPORE.getCode()) instanceof SgAirCalculator);
	}
	
	@Test
	public void shouldGetSgCalculatorForAU() {
		assertTrue(factory.getCalculator(Country.AUSTRALIA.getCode()) instanceof SgAirCalculator);
	}
	
	@Test
	public void shouldGetSgCalculatorForNz() {
		assertTrue(factory.getCalculator(Country.NEW_ZEALAND.getCode()) instanceof SgAirCalculator);
	}
	
	
}
