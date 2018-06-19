package com.cwt.bpg.cbt.exchange.order;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.cwt.bpg.cbt.exchange.order.model.ExchangeOrder;

public class ExchangeOrderServiceTest {

	@Mock
	private ExchangeOrderRepository repo;
	
	@InjectMocks
	private ExchangeOrderService service;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}
  
	public void shouldCallSaveOrUpdate() {
		ExchangeOrder eo = new ExchangeOrder();
		eo.setCountryCode("HK");
		service.saveExchangeOrder(eo);
		verify(repo, times(1)).saveOrUpdate(eo);
	}

}
