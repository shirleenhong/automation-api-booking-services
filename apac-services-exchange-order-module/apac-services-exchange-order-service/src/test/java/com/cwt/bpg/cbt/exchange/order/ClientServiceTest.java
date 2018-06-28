package com.cwt.bpg.cbt.exchange.order;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

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
		service.getAll();
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
	
	@Test
	public void canGetSingleClientUsingId() {
		Client client = new Client();
		final int id = 9;
		when(repository.get(id)).thenReturn(client);
		Client result = service.getClient(id);
		assertNotNull(result);
		verify(repository, times(1)).get(id);
	}
	
	@Test
	public void canGetSingleClientUsingClientAccountNumber() {
		Client client = new Client();
		final String clientAccountNumber = "clientAccountNumber";
		when(repository.getClient(clientAccountNumber)).thenReturn(client);
		Client result = service.getClient(clientAccountNumber);
		assertNotNull(result);
		verify(repository, times(1)).getClient(clientAccountNumber);
	}
	
	@Test
	public void canGetDefaultClient() {
		Client client = new Client();
		final int id = -1;
		when(repository.get(id)).thenReturn(client);
		Client result = service.getDefaultClient();
		assertNotNull(result);
		verify(repository, times(1)).get(id);
	}
	
}
