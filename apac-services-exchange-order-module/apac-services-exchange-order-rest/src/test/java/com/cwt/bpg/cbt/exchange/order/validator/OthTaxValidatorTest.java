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
		input.setOthTax1(null);
		input.setOthTax2(null);
		input.setOthTax3(null);

		thrown.expect(IllegalArgumentException.class);
		validator.validate(input);
		
		verify(validator, times(1)).validate(input);
	}
	
	@Test
	public void shouldCheckTaxSuccessWhenAllTaxesHaveValue() {
		IndiaAirFeesInput input = new IndiaAirFeesInput();
		input.setOthTax1(new BigDecimal(50));
		input.setOthTax2(new BigDecimal(50));
		input.setOthTax3(new BigDecimal(50));

		validator.validate(input);
		assertNotNull(validator);
	}

	@Test
	public void shouldCheckTaxSuccessOnlyOneTaxIsNull() {
		IndiaAirFeesInput input = new IndiaAirFeesInput();
		input.setOthTax1(new BigDecimal(50));
		input.setOthTax2(new BigDecimal(50));
		input.setOthTax3(null);

		validator.validate(input);
		assertNotNull(validator);
	}
}
