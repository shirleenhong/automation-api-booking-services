package com.cwt.bpg.cbt.exchange.order;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.cwt.bpg.cbt.exchange.order.model.AirlineRule;

public class AirlineRulesControllerTest {

	@Mock
	private AirlineRuleService service;

	@InjectMocks
	private AirlineRulesController airlineRulesController;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void canGetAirlineRules() {
		when(service.getAll()).thenReturn(new ArrayList<AirlineRule>());
		
		ResponseEntity<?> airlineRules = airlineRulesController.getAirlineRules();
		
		assertEquals(HttpStatus.OK, airlineRules.getStatusCode());
		verify(service, times(1)).getAll();
	}

	@Test
	public void canPutAirlineRules() {
		final AirlineRule airlineRule = new AirlineRule();
		when(service.save(airlineRule)).thenReturn(airlineRule);

		ResponseEntity<?> client = airlineRulesController.putAirlineRules(airlineRule);

		assertEquals(HttpStatus.OK, client.getStatusCode());
		verify(service, times(1)).save(airlineRule);
	}

	@Test
	public void canRemoveAirlineRules() {
		final String airlineCode = "A1";
		when(service.delete(airlineCode)).thenReturn(airlineCode);
		
		ResponseEntity<?> client = airlineRulesController.removeAirlineRules(airlineCode);
		assertEquals(HttpStatus.OK, client.getStatusCode());
		verify(service, times(1)).delete(airlineCode);
	}
}
