package com.cwt.bpg.cbt.exchange.order;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

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
	
	@Test
	public void canPutFaresShopOptions() {
		ResponseEntity<?> faresShopOptions = faresShopController.putFaresShopOptions(ArrayList::new);
		assertEquals(HttpStatus.OK, faresShopOptions.getStatusCode());
	}
	
	@Test
	public void canRemoveFaresShopOptions() {
		ResponseEntity<?> faresShopOptions = faresShopController.removeFaresShopOptions(ArrayList::new);
		assertEquals(HttpStatus.OK, faresShopOptions.getStatusCode());
	}
}
