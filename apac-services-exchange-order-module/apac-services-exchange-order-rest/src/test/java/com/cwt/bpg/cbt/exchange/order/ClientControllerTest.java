package com.cwt.bpg.cbt.exchange.order;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

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
	private ClientService clientPricingService;
	
	@InjectMocks
	private ClientController clientController;
	
	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void canGetClientById() {
			final int id = 8;
			ResponseEntity<?> client = clientController.getClient(id);
			verify(clientPricingService, times(1)).getClient(id);
			assertEquals(HttpStatus.OK,  client.getStatusCode());
	}

	@Test
	public void canPutClientPricing() {
		Client newClient = new Client();
		ResponseEntity<?> client = clientController.putClient(newClient);
		verify(clientPricingService, times(1)).save(newClient);
		assertEquals(HttpStatus.OK, client.getStatusCode());
	}
	
	@Test
	public void canRemoveClientPricing() {
		final int keyValue = 0;
		ResponseEntity<?> client = clientController.removeClient(keyValue);
		verify(clientPricingService, times(1)).delete(keyValue);
		assertEquals(HttpStatus.OK, client.getStatusCode());
	}
}