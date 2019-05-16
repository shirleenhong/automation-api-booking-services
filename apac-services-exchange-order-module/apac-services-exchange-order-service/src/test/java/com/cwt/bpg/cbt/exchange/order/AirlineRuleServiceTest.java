package com.cwt.bpg.cbt.exchange.order;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.cwt.bpg.cbt.exchange.order.model.AirlineRule;

public class AirlineRuleServiceTest {

	@Mock
	private AirlineRuleRepository repository;

	@InjectMocks
	private AirlineRuleService service;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void canGetAllAirlineRule() {
		List<AirlineRule> value = mock(List.class);
		when(service.getAll()).thenReturn(value);

		List<AirlineRule> result = service.getAll();

		assertNotNull(result);
		verify(repository, times(1)).getAll();
	}

	@Test
	public void canPutAirlineRule() {
		AirlineRule airlineRule = mock(AirlineRule.class);
		when(service.save(airlineRule)).thenReturn(airlineRule);

		AirlineRule result = service.save(airlineRule);

		assertNotNull(result);
		verify(repository, times(1)).put(airlineRule);
	}

	@Test
	public void canRemoveAirlineRule() {
		String airlineCode = "UK";
		when(service.delete(airlineCode)).thenReturn(airlineCode);

		String result = service.delete(airlineCode);

		assertNotNull(result);
		verify(repository, times(1)).remove(airlineCode);
	}
	
	@Test
	public void canGetAirlineWithAirlineCode() {
		when(service.getAirlineRule(anyString())).thenReturn(new AirlineRule());
		
		assertNotNull(service.getAirlineRule(anyString()));
		verify(repository, times(1)).get(anyString());
	}

}
