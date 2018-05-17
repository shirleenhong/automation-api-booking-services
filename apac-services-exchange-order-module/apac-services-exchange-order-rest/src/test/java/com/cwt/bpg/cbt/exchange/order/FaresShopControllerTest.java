package com.cwt.bpg.cbt.exchange.order;

import static org.junit.Assert.*;

import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class FaresShopControllerTest {

	private FaresShopController faresShopController = new FaresShopController();
	
	@Test
	public void canGetFaresShopOptions() {
		ResponseEntity<?> faresShopOptions = faresShopController.getFaresShopOptions();
		assertEquals(HttpStatus.OK, faresShopOptions.getStatusCode());
	}
}
