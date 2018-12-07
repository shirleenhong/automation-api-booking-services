package com.cwt.bpg.cbt.calculator.config;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

public class CommonConfigTest {

	private CommonConfig config = new CommonConfig();
	
	@Test
	public void testScaleConfig() {
		assertNotNull(config.scaleConfig());
	}
	
	@Test
	public void testRoundingConfig() {
		assertNotNull(config.roundingConfig());
	}
}
