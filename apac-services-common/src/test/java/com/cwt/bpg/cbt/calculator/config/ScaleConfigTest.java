package com.cwt.bpg.cbt.calculator.config;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.core.env.Environment;

import com.cwt.bpg.cbt.calculator.model.Country;

public class ScaleConfigTest {

	@Mock
	private Environment env;
	
	@InjectMocks
	private ScaleConfig config;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		
		Mockito.when(env.getProperty(Mockito.eq("com.cwt.bpg.cbt.calc.scale.HK"), Mockito.eq(Integer.class))).thenReturn(2);
		Mockito.when(env.getRequiredProperty(Mockito.eq("com.cwt.bpg.cbt.calc.scale.Default"), Mockito.eq(Integer.class))).thenReturn(1);
	}
	
	@Test
	public void shouldReturnHKProp() {
		assertEquals(2, config.getScale(Country.HONG_KONG.getCode()));
	}
	
	@Test
	public void shouldReturnDefault() {
		assertEquals(1, config.getScale(null));
	}

}
