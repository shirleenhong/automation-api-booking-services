package com.cwt.bpg.cbt.exchange.order;

import static org.junit.Assert.assertEquals;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.cwt.bpg.cbt.exchange.order.model.Client;

public class ClientControllerTest {

	@Mock
	private ClientService clientService;
	
	@InjectMocks
	private ClientController clientController;
	
	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void canGetClientById() {
		final String clientAccountNumber = "123456";
		ResponseEntity<?> client = clientController.getClient(clientAccountNumber);
		verify(clientService, times(1)).getClient(clientAccountNumber);
		assertEquals(HttpStatus.OK, client.getStatusCode());
	}

	@Test
	public void canPutClientPricing() {
		Client newClient = new Client();
		List<Client> clientList = new ArrayList<>();
		when(clientService.getAll()).thenReturn(clientList);

		ResponseEntity<?> client = clientController.putClient(newClient);
		verify(clientService, times(1)).save(newClient);
		assertEquals(HttpStatus.OK, client.getStatusCode());
	}

	@Test
	public void canRemoveClientPricing() {
		final String clientAccountNumber = "123456";
		when(clientService.delete(clientAccountNumber)).thenReturn(clientAccountNumber);

		List<Client> clientList = new ArrayList<>();
		when(clientService.getAll()).thenReturn(clientList);

		ResponseEntity<?> client = clientController.removeClient(clientAccountNumber);
		verify(clientService, times(1)).delete(clientAccountNumber);
		assertEquals(HttpStatus.OK, client.getStatusCode());
	}

	@Test
	public void removeReturnsNotFoundWhenRecordDoesNotExist() {
		final String clientAccountNumber = "123456";
		when(clientService.delete(clientAccountNumber)).thenReturn("");

		List<Client> clientList = new ArrayList<>();
		when(clientService.getAll()).thenReturn(clientList);

		ResponseEntity<?> client = clientController.removeClient(clientAccountNumber);
		verify(clientService, times(1)).delete(clientAccountNumber);
		assertEquals(HttpStatus.NOT_FOUND, client.getStatusCode());
	}

	@Test
	public void canGetAllClients() {

		ResponseEntity<?> client = clientController.getAllClients();
		verify(clientService, times(1)).getAll();
		assertEquals(HttpStatus.OK, client.getStatusCode());
	}
}
