package com.cwt.bpg.cbt.exchange.order;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

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
		ResponseEntity<?> airlineRules = airlineRulesController.getAirlineRules();
		assertEquals(HttpStatus.OK,  airlineRules.getStatusCode());
	}
	
	@Test
	public void canPutAirlineRules() {
		final AirlineRule airlineRule = new AirlineRule();
		ResponseEntity<?> client = airlineRulesController.putAirlineRules(airlineRule);
		assertEquals(HttpStatus.OK, client.getStatusCode());
	}
	
	@Test
	public void canRemoveAirlineRules() {
		ResponseEntity<?> client = airlineRulesController.removeAirlineRules("CAT1");
		assertEquals(HttpStatus.OK, client.getStatusCode());
	}
}
