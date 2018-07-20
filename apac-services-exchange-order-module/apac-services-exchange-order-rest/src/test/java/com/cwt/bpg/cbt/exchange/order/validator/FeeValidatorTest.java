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


public class FeeValidatorTest {

	private FeeValidator validator = new FeeValidator();

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Rule
	public ExpectedException thrown = ExpectedException.none();
	
	@Test
	public void shouldCheckFeeNull() {
		IndiaAirFeesInput input = new IndiaAirFeesInput();
		input.setFeeOverride(true);
		input.setFee(null);
		
		thrown.expect(IllegalArgumentException.class);
		validator.validate(input);
		
		verify(validator, times(1)).validate(input);
	}
	
	@Test
	public void shouldCheckFeeSuccess() {
		IndiaAirFeesInput input = new IndiaAirFeesInput();
		input.setFeeOverride(false);
		input.setFee(new BigDecimal(50));
		
		validator.validate(input);
		assertNotNull(validator);
	}
}