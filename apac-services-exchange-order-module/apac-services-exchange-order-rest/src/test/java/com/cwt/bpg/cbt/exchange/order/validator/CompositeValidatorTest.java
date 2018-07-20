package com.cwt.bpg.cbt.exchange.order.validator;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import com.cwt.bpg.cbt.exchange.order.model.IndiaAirFeesInput;

public class CompositeValidatorTest {

	private CompositeValidator validator;

	@Mock
	private FeeValidator feeValidator;

	@Mock
	private OthTaxValidator othTaxValidator;

	@Before
	public void setUp() {
		initMocks(this);

		validator = new CompositeValidator();
	}

	@Test
	public void shouldInvokeAllValidators() {
		validator.setValidators(Arrays.asList(feeValidator, othTaxValidator));

		IndiaAirFeesInput input = new IndiaAirFeesInput();

		validator.validate(input);

		verify(feeValidator, times(1)).validate(input);
		verify(othTaxValidator, times(1)).validate(input);
	}

	@Test
	public void shouldAddValidator() {
		validator.addValidator(feeValidator);

		List<Validator> validators = validator.getValidators();

		assertThat(validators, hasItem(feeValidator));
	}
}
