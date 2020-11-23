package com.cwt.bpg.cbt.exchange.order;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.cwt.bpg.cbt.exchange.order.model.Client;
import com.cwt.bpg.cbt.exchange.order.model.FlatTransactionFee;

public class ClientControllerTest {

	@Mock
	private ClientService clientService;

	@Mock
	private FlatTransactionFeeService clientTransactionFeeService;
	
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
		List<Client> newClient = Arrays.asList(new Client());
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
		assertEquals(HttpStatus.NO_CONTENT, client.getStatusCode());
	}

	@Test
	public void canGetAllClients() {

		ResponseEntity<?> client = clientController.getAllClients();
		verify(clientService, times(1)).getAll();
		assertEquals(HttpStatus.OK, client.getStatusCode());
	}

	@Test
	public void canPutClientTransactionFee()
	{
		FlatTransactionFee clientTransactionFee = new FlatTransactionFee();
		ResponseEntity<?> client = clientController.putClientTransactionFee("123456789", clientTransactionFee);
		verify(clientTransactionFeeService).save(clientTransactionFee);
		assertEquals(HttpStatus.OK, client.getStatusCode());
	}


	@Test
	public void canRemoveClientTransactionFee()
	{
		final String clientAccountNumber = "0011122";
		when(clientTransactionFeeService.delete(clientAccountNumber)).thenReturn("");
		ResponseEntity<?> client = clientController.removeClientTransactionFee(clientAccountNumber);
		assertEquals(HttpStatus.NO_CONTENT, client.getStatusCode());

		when(clientTransactionFeeService.delete(clientAccountNumber)).thenReturn("row deleted");
		client = clientController.removeClientTransactionFee(clientAccountNumber);
		assertEquals(HttpStatus.OK, client.getStatusCode());
		verify(clientTransactionFeeService, times(2)).delete(clientAccountNumber);
	}

	@Test
	public void canGetClientTransactionFee()
	{
		final String clientAccountNumber = "123456789";
		when(clientTransactionFeeService.getTransactionFee(clientAccountNumber)).thenReturn(null);
		ResponseEntity<?> client = clientController.getClientTransactionFee(clientAccountNumber);
		assertEquals(HttpStatus.NOT_FOUND, client.getStatusCode());

		FlatTransactionFee clientTransactionFee = new FlatTransactionFee();

		when(clientTransactionFeeService.getTransactionFee(clientAccountNumber)).thenReturn(clientTransactionFee);
		client = clientController.getClientTransactionFee(clientAccountNumber);

		verify(clientTransactionFeeService, times(2)).getTransactionFee(clientAccountNumber);
		assertEquals(HttpStatus.OK, client.getStatusCode());
	}
}
