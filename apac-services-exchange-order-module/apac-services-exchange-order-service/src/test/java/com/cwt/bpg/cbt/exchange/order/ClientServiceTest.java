package com.cwt.bpg.cbt.exchange.order;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.cwt.bpg.cbt.exchange.order.model.Client;
import com.cwt.bpg.cbt.exchange.order.model.ClientPricing;
import com.cwt.bpg.cbt.exchange.order.model.india.AirFeesDefaultsInput;

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
		final String clientAccountNumber = "123456";
		service.delete(clientAccountNumber);
		verify(repository, times(1)).remove(clientAccountNumber);
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
	public void shouldGetClientPricings() {
		Client client = new Client();
		List<ClientPricing> clientPricings = new ArrayList<>();
		ClientPricing clientPricing = new ClientPricing();
		int clientId = 12345;
		String tripType = "I";
		
		clientPricing.setCmpid(1);
		clientPricing.setTripType(tripType);
		clientPricings.add(clientPricing);
		client.setClientPricings(clientPricings);
		
		AirFeesDefaultsInput input = new AirFeesDefaultsInput();
		input.setClientId(clientId);
		input.setTripType(tripType);
		when(repository.get(clientId)).thenReturn(client);
		List<ClientPricing> result = service.getClientPricings(input);
		
		assertFalse(result.isEmpty());
		verify(repository, times(1)).get(clientId);
	}
	
	@Test
	public void shouldGetClientPricingsNotEqualTripType() {
		Client client = new Client();
		List<ClientPricing> clientPricings = new ArrayList<>();
		ClientPricing clientPricing = new ClientPricing();
		int clientId = 12345;
		String tripType = "I";
		
		clientPricing.setCmpid(1);
		clientPricing.setTripType("D");
		clientPricings.add(clientPricing);
		client.setClientPricings(clientPricings);
		
		AirFeesDefaultsInput input = new AirFeesDefaultsInput();
		input.setClientId(clientId);
		when(repository.get(clientId)).thenReturn(client);
		List<ClientPricing> result = service.getClientPricings(input);
		
		assertTrue(result.isEmpty());
		verify(repository, times(1)).get(clientId);
	}
	
	@Test
	public void shouldGetClientPricingsWithNullTripType() {
		Client client = new Client();
		List<ClientPricing> clientPricings = new ArrayList<>();
		ClientPricing clientPricing = new ClientPricing();
		int clientId = 12345;
		
		clientPricing.setCmpid(1);
		clientPricing.setTripType("D");
		clientPricings.add(clientPricing);
		client.setClientPricings(clientPricings);
		
		AirFeesDefaultsInput input = new AirFeesDefaultsInput();
		input.setClientId(clientId);
		when(repository.get(clientId)).thenReturn(client);
		List<ClientPricing> result = service.getClientPricings(input);
		
		assertTrue(result.isEmpty());
		verify(repository, times(1)).get(clientId);
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
