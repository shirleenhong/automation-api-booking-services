package com.cwt.bpg.cbt.calculator.config;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.cwt.bpg.cbt.calculator.CommonCalculator;

public class CommonConfigTest {

	CommonConfig config = new CommonConfig();
	
	@Test
	public void shouldCreateCommonCalculator() {
		assertTrue(config.commonCalculator() instanceof CommonCalculator);
	}

	@Test
	public void testScaleConfig() {
		assertTrue(config.scaleConfig() instanceof ScaleConfig);
	}

}
