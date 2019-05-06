package com.cwt.bpg.cbt.smartflow.validator;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import com.cwt.bpg.cbt.smartflow.model.Codif;

public class CodifValidatorTest {

	private CodifValidator validator = new CodifValidator();
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void shouldCheckHarpNo() {
		Codif input = new Codif();
		input.setHarpNo("123456");
		
		validator.validate(input);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void shouldThrowExceptionWhenHarpNoIsInvalid() {
		Codif input = new Codif();
		input.setHarpNo("1234567");
		
		validator.validate(input);
	}
}
