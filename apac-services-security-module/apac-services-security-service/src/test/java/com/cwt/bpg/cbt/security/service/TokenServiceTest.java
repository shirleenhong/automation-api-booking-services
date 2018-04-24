package com.cwt.bpg.cbt.security.service;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.mockito.Matchers.anyString;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import com.cwt.bpg.cbt.security.api.model.Token;
import com.cwt.bpg.cbt.security.repository.TokenRepository;

@RunWith(MockitoJUnitRunner.class)
public class TokenServiceTest {

	@InjectMocks
	private TokenService service;
	
	@Mock
	private TokenRepository repo;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}
		
	@Test
	public void shouldReturnTrue() throws IOException {
		
		Mockito.when(repo.getToken(anyString())).thenReturn(new Token());
		assertTrue(service.isTokenExist("tokenkey"));
	}
	
	@Test
	public void shouldReturnFalse() throws IOException {
		
		Mockito.when(repo.getToken(anyString())).thenReturn(null);
		assertFalse(service.isTokenExist("tokenkey"));
	}	
}
