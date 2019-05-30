package com.cwt.bpg.cbt.exchange.order.validator;

import org.junit.Test;

import com.cwt.bpg.cbt.exchange.order.model.FeesInput;

public class ClientIdValidatorTest {
	
	private ClientIdValidator validator = new ClientIdValidator();
	
	@Test(expected = IllegalArgumentException.class)
	public void shouldThrowException() {
		FeesInput input = new FeesInput();
		input.setClientId(0);
		validator.validate(input);
	}
	
	@Test
	public void shouldAcceptValidClientId() {
		FeesInput input = new FeesInput();
		input.setClientId(1);
		validator.validate(input);
	}

}
