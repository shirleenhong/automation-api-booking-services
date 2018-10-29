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
		
		Mockito.when(env.getProperty(Mockito.eq("com.cwt.bpg.cbt.calc.rounding.HK"), Mockito.eq(Integer.class))).thenReturn(0);
		Mockito.when(env.getRequiredProperty(Mockito.eq("com.cwt.bpg.cbt.calc.rounding.Default"), Mockito.eq(Integer.class))).thenReturn(4);
	}
	
	@Test
	public void shouldReturnHKProp() {
		assertEquals(RoundingMode.UP, config.getRoundingMode(Country.HONG_KONG.getCode()));
	}
	
	@Test
	public void shouldReturnDefault() {
		assertEquals(RoundingMode.HALF_UP, config.getRoundingMode(null));
	}

}
