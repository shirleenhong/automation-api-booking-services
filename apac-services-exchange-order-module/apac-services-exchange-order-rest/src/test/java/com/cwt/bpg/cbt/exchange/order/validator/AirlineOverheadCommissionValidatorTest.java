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

public class AirlineOverheadCommissionValidatorTest {

	private AirlineOverheadCommissionValidator validator = new AirlineOverheadCommissionValidator();

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Test
	public void shouldCheckAirlineOverheadCommissionByPercentNull() {
		IndiaAirFeesInput input = new IndiaAirFeesInput();
		input.setAirlineOverheadCommissionByPercent(true);
		input.setAirlineOverheadCommissionPercent(null);
		
		thrown.expect(IllegalArgumentException.class);
		validator.validate(input);

		verify(validator, times(1)).validate(input);
	}

	@Test
	public void shouldCheckAirlineOverheadCommissionNull() {
		IndiaAirFeesInput input = new IndiaAirFeesInput();
		input.setAirlineOverheadCommissionByPercent(false);
		input.setAirlineOverheadCommission(null);

		thrown.expect(IllegalArgumentException.class);
		validator.validate(input);
		
		verify(validator, times(1)).validate(input);
	}

	@Test
	public void shouldCheckAirlineOverheadCommissionByPercentSuccess() {
		IndiaAirFeesInput input = new IndiaAirFeesInput();
		input.setAirlineOverheadCommissionByPercent(false);
		input.setAirlineOverheadCommission(new BigDecimal(50));

		validator.validate(input);
		assertNotNull(validator);
	}

	@Test
	public void shouldCheckAirlineOverheadCommissionSuccess() {
		IndiaAirFeesInput input = new IndiaAirFeesInput();
		input.setAirlineOverheadCommissionByPercent(true);
		input.setAirlineOverheadCommission(new BigDecimal(50));
		input.setAirlineOverheadCommissionPercent(5d);

		validator.validate(input);
		assertNotNull(validator);
	}
}