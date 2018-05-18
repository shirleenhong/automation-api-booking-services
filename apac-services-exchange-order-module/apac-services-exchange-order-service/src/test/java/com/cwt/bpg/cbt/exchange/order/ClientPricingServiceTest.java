package com.cwt.bpg.cbt.exchange.order;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import com.cwt.bpg.cbt.exchange.order.model.AirlineRule;
import com.cwt.bpg.cbt.exchange.order.model.Client;

public class ClientPricingServiceTest {
	
	private ClientPricingService service = new ClientPricingService();

	@Test
	public void canGetAllClientPricing() {
		List<AirlineRule> all = service.getAll();
		assertEquals(null, all);
	}
	
	@Test
	public void canDeleteClientPricing() {
		String result = service.delete("OA");
		assertEquals("OA", result);
	}
	
	@Test
	public void canSaveClientPricing() {
		Client client = new Client();
		Client result = service.save(client);
		assertEquals(null, result);
	}


}
