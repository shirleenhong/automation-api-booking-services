package com.cwt.bpg.cbt.security.service;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

import com.cwt.bpg.cbt.security.api.model.Token;
import com.cwt.bpg.cbt.security.repository.TokenRepository;

@RunWith(MockitoJUnitRunner.class)
public class TokenServiceTest {

	@InjectMocks
	private TokenService service;

	@Mock
	private TokenRepository repo;

	private static final String VALID_TOKEN = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhcHAiOiJUZXN0In0.KrQkG8lz8n4GaHl4uv2WAhdIKRwn_44riqcBKg9gP5w";

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		ReflectionTestUtils.setField(service, "secretKey", "test-secret");
	}

	@Test
	public void shouldReturnTrue() {
		Token token = new Token();
		token.setKey(VALID_TOKEN);
		when(repo.getToken(anyString())).thenReturn(token);

		assertTrue(service.isTokenExist(VALID_TOKEN));
	}

	@Test
	public void shouldReturnFalse() {
		when(repo.getToken(anyString())).thenReturn(null);

		assertFalse(service.isTokenExist(null));
	}
}
