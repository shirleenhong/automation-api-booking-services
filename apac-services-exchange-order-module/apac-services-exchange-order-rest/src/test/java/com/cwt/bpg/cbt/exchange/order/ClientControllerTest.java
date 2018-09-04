package com.cwt.bpg.cbt.exchange.order;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

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
			final String clientAccountNumber = "123456";
			ResponseEntity<?> client = clientController.getClient(clientAccountNumber);
			verify(clientPricingService, times(1)).getClient(clientAccountNumber);
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
		final String clientAccountNumber = "123456";
		when(clientPricingService.delete(clientAccountNumber)).thenReturn(clientAccountNumber);

		ResponseEntity<?> client = clientController.removeClient(clientAccountNumber);
		verify(clientPricingService, times(1)).delete(clientAccountNumber);
		assertEquals(HttpStatus.OK, client.getStatusCode());
	}

    @Test
    public void removeReturnsNotFoundWhenRecordDoesNotExist() {
        final String clientAccountNumber = "123456";
        when(clientPricingService.delete(clientAccountNumber)).thenReturn("");

        ResponseEntity<?> client = clientController.removeClient(clientAccountNumber);
        verify(clientPricingService, times(1)).delete(clientAccountNumber);
        assertEquals(HttpStatus.NOT_FOUND, client.getStatusCode());
    }

}
