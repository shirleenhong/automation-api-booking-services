package com.cwt.bpg.cbt.exchange.order;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.cwt.bpg.cbt.exchange.order.model.Client;

public class ClientServiceTest {
	
	@Mock
	private ClientRepository repository;
	
	@InjectMocks
	private ClientService service;
	
	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void canGetAllClient() {
		service.getAllClients();
		verify(repository, times(1)).getAll();
	}
	
	@Test
	public void canDeleteClient() {
		final int key = 1;
		service.delete(key);
		verify(repository, times(1)).remove(key);
	}
	
	@Test
	public void canSaveClient() {
		Client client = new Client();
		Client result = service.save(client);
		assertEquals(null, result);
	}
	
}
