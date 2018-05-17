package com.cwt.bpg.cbt.exchange.order;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ClientPricingControllerTest {

	private ClientPricingController clientPricingController = new ClientPricingController();
	
	@Test
	public void canGetClientById() {
			ResponseEntity<?> client = clientPricingController.getClient(8);
			assertEquals(HttpStatus.OK,  client.getStatusCode());
	}

}
