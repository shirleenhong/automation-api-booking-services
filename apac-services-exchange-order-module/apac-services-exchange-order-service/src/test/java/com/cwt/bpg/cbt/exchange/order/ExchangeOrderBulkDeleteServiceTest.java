package com.cwt.bpg.cbt.exchange.order;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.cwt.bpg.cbt.exchange.order.model.ExchangeOrder;

public class ExchangeOrderBulkDeleteServiceTest {

	@InjectMocks
	private ExchangeOrderBulkDeleteService service;

	@Mock
	private ExchangeOrderRepository repo;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void shouldCallBulkDeleteExchangeOrder() {
		List<ExchangeOrder> exchangeOrderList = new ArrayList<>();
		ExchangeOrder exchangeOrder = createExchangeOrder();
		exchangeOrderList.add(exchangeOrder);
		
		when(repo.getExchangeOrderNoRecordLocator()).thenReturn(exchangeOrderList);

		service.bulkDeleteExchangeOrder();
		verify(repo, times(1)).getExchangeOrderNoRecordLocator();
		verify(repo, times(1)).remove(exchangeOrder);
	}

	@Test
	public void shouldCallBulkDeleteExchangeOrderEmptyList() {
		List<ExchangeOrder> exchangeOrderList = new ArrayList<>();
		ExchangeOrder exchangeOrder = new ExchangeOrder();
		when(repo.getExchangeOrderNoRecordLocator()).thenReturn(exchangeOrderList);

		service.bulkDeleteExchangeOrder();
		verify(repo, times(1)).getExchangeOrderNoRecordLocator();
		verify(repo, times(0)).remove(exchangeOrder);
	}

	private ExchangeOrder createExchangeOrder() {
		ExchangeOrder exchangeOrder = new ExchangeOrder();
		exchangeOrder.setEoNumber("123098");
		
		return exchangeOrder;
	}

}
