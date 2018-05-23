package com.cwt.bpg.cbt.exchange.order;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.cwt.bpg.cbt.exchange.order.model.Client;

public class ClientPricingServiceTest {
	
	@Mock
	private ClientPricingRepository repository;
	
	@InjectMocks
	private ClientPricingService service;
	
	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void canGetAllClientPricing() {
		List<Client> all = service.getAll();
		verify(repository, times(1)).getAll();
	}
	
	@Test
	public void canDeleteClientPricing() {
		final int key = 1;
		String result = service.delete(key);
		verify(repository, times(1)).remove(key);
	}
	
	@Test
	public void canSaveClientPricing() {
		Client client = new Client();
		Client result = service.save(client);
		assertEquals(null, result);
	}


}
