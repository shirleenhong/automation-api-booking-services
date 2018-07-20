package com.cwt.bpg.cbt.exchange.order.validator;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.MockitoAnnotations;

import com.cwt.bpg.cbt.exchange.order.model.IndiaAirFeesInput;


public class OthTaxValidatorTest {

	private OthTaxValidator validator = new OthTaxValidator();

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Rule
	public ExpectedException thrown = ExpectedException.none();
	
	@Test
	public void shouldCheckTaxNull() {
		IndiaAirFeesInput input = new IndiaAirFeesInput();
		input.setTax1(null);
		input.setTax2(null);

		thrown.expect(IllegalArgumentException.class);
		validator.validate(input);
		
		verify(validator, times(1)).validate(input);
	}
	
	@Test
	public void shouldCheckTaxSuccess() {
		IndiaAirFeesInput input = new IndiaAirFeesInput();
		input.setTax1(new BigDecimal(50));
		input.setTax2(new BigDecimal(50));

		validator.validate(input);
		assertNotNull(validator);
	}
}