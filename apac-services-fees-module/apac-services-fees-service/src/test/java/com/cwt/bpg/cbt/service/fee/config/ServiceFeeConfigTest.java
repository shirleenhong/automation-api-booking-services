package com.cwt.bpg.cbt.service.fee.config;

import static org.junit.Assert.*;

import org.junit.Test;

import com.cwt.bpg.cbt.service.fee.calculator.ServiceFeeCalculator;

public class ServiceFeeConfigTest {

	@Test
	public void shouldReturnServiceFeeCalculator() {
		ServiceFeeConfig config = new ServiceFeeConfig();
		assertTrue(config.serviceFeeCalculator() instanceof ServiceFeeCalculator);
	}

}
