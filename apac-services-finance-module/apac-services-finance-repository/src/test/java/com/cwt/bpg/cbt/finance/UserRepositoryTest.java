package com.cwt.bpg.cbt.finance;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

public class UserRepositoryTest {

	@Test
	public void shouldCreateUserRepositoryInstance() {
		UserRepository repository = new UserRepository();

		assertNotNull(repository);
	}
}
