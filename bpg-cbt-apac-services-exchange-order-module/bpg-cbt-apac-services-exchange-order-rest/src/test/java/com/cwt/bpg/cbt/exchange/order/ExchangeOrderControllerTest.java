package com.cwt.bpg.cbt.exchange.order;

import static org.junit.Assert.*;

import org.junit.Test;
import org.springframework.http.ResponseEntity;

public class ExchangeOrderControllerTest {
	
	private ExchangeOrderController exchangeOrder = new ExchangeOrderController();

	@Test
	public void test() {
		ResponseEntity<String> products = exchangeOrder.getProducts("HK");
		
		assertEquals("Products", products.getBody());
	}

}
