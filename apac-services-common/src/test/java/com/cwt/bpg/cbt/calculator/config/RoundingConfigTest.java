package com.cwt.bpg.cbt.calculator.config;

import com.cwt.bpg.cbt.calculator.model.Country;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.core.env.Environment;

import java.math.RoundingMode;

import static org.junit.Assert.assertEquals;

public class RoundingConfigTest {

	@Mock
	private Environment env;
	
	@InjectMocks
	private RoundingConfig config;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		
		Mockito.when(env.getRequiredProperty("com.cwt.bpg.cbt.calc.rounding.nettFare.Default", Integer.class)).thenReturn(0);
		Mockito.when(env.getRequiredProperty("com.cwt.bpg.cbt.calc.rounding.nettCost.Default", Integer.class)).thenReturn(0);
		Mockito.when(env.getRequiredProperty("com.cwt.bpg.cbt.calc.rounding.merchantFee.Default", Integer.class)).thenReturn(0);
		Mockito.when(env.getRequiredProperty("com.cwt.bpg.cbt.calc.rounding.totalSellingFare.Default", Integer.class)).thenReturn(0);
		Mockito.when(env.getRequiredProperty("com.cwt.bpg.cbt.calc.rounding.commission.Default", Integer.class)).thenReturn(1);
	}
	
	@Test
	public void shouldReturnNettFareRoundingWhenCountryCodeIsNull() {
		assertEquals(RoundingMode.UP, config.getRoundingMode("nettFare", null));
	}

	@Test
	public void shouldReturnNettCostRounding() {
		assertEquals(RoundingMode.UP, config.getRoundingMode("nettCost", "SG"));
	}

	@Test
	public void shouldReturnMerchantFeeRounding() {
		assertEquals(RoundingMode.UP, config.getRoundingMode("merchantFee", "HK"));
	}

	@Test
	public void shouldReturnTotalSellingFareRounding() {
		assertEquals(RoundingMode.UP, config.getRoundingMode("totalSellingFare", "HK"));
	}

	@Test
	public void shouldReturnCommissionRounding() {
		assertEquals(RoundingMode.DOWN, config.getRoundingMode("commission", "HK"));
	}
}
